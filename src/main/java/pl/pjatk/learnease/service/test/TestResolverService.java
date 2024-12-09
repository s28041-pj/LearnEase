package pl.pjatk.learnease.service.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjatk.learnease.entity.test.DiagnosticTestResult;
import pl.pjatk.learnease.entity.test.DiagnosticTestTemplate;
import pl.pjatk.learnease.entity.test.dto.question.AnsweredQuestion;
import pl.pjatk.learnease.entity.test.dto.question.CorrectDiagnosticQuestion;
import pl.pjatk.learnease.entity.test.dto.test.DiagnosticTestResultDTO;
import pl.pjatk.learnease.entity.test.dto.test.ResolvedDiagnosticTest;
import pl.pjatk.learnease.entity.user.User;
import pl.pjatk.learnease.mapper.DiagnosticTestMapper;
import pl.pjatk.learnease.repository.test.DiagnosticTestResultRepository;
import pl.pjatk.learnease.service.UserSubjectLevelService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static pl.pjatk.learnease.configure.UserContextHolder.getContextUser;

@Service
@RequiredArgsConstructor
public class TestResolverService {
    private final DiagnosticTestResultRepository diagnosticTestResultRepository;
    private final UserSubjectLevelService userSubjectLevelService;
    private final DiagnosticTestMapper diagnosticTestMapper;
    private final QuestionService questionService;

    public DiagnosticTestResultDTO solveDiagnosticTest(DiagnosticTestTemplate foundDiagnosticTest, ResolvedDiagnosticTest resolvedDiagnosticTest) {
        return processDiagnosticTest(foundDiagnosticTest, resolvedDiagnosticTest, false);
    }

    public DiagnosticTestResultDTO resolveDiagnosticTest(DiagnosticTestTemplate diagnosticTestTemplate, ResolvedDiagnosticTest resolvedDiagnosticTest) {
        return processDiagnosticTest(diagnosticTestTemplate, resolvedDiagnosticTest, true);
    }

    private DiagnosticTestResultDTO processDiagnosticTest(DiagnosticTestTemplate diagnosticTestTemplate,
                                                          ResolvedDiagnosticTest resolvedDiagnosticTest,
                                                          boolean checkForExistingResult) {
        List<CorrectDiagnosticQuestion> correctDiagnosticQuestions = questionService.getCorrectDiagnosticQuestions(diagnosticTestTemplate);
        Float score = getTestScore(correctDiagnosticQuestions, resolvedDiagnosticTest.answeredQuestions());

        DiagnosticTestResult diagnosticTestResult;
        if (checkForExistingResult) {
            diagnosticTestResult = getOrUpdateExistingTestResult(diagnosticTestTemplate, score);
        } else {
            diagnosticTestResult = createDiagnosticTestResult(diagnosticTestTemplate, score);
        }

        userSubjectLevelService.createOrUpdateUserSubjectLevel(diagnosticTestResult);
        return diagnosticTestMapper.toDiagnosticTestResultDTO(diagnosticTestResult);
    }

    private DiagnosticTestResult getOrUpdateExistingTestResult(DiagnosticTestTemplate diagnosticTestTemplate, Float score) {
        User contextUser = getContextUser();
        Optional<DiagnosticTestResult> foundDiagnosticTestResult = diagnosticTestResultRepository.findDiagnosticTestResultByUsernameAndSubject(
                contextUser.getUsername(), diagnosticTestTemplate.getTestName());

        return foundDiagnosticTestResult
                .map(existingResult -> updateDiagnosticTestResult(existingResult, score))
                .orElseGet(() -> createDiagnosticTestResult(diagnosticTestTemplate, score));
    }


    private DiagnosticTestResult createDiagnosticTestResult(DiagnosticTestTemplate diagnosticTestTemplate,
                                                            Float score) {
        DiagnosticTestResult diagnosticTestResult = new DiagnosticTestResult();
        diagnosticTestResult.setTestTemplate(diagnosticTestTemplate);
        diagnosticTestResult.setUser(getContextUser());
        diagnosticTestResult.setDateTaken(LocalDateTime.now());
        diagnosticTestResult.setScore(score);
        diagnosticTestResult.setPreviousScore(0f);
        return diagnosticTestResultRepository.save(diagnosticTestResult);
    }

    private DiagnosticTestResult updateDiagnosticTestResult(DiagnosticTestResult diagnosticTestResult,
                                                            Float score) {
        diagnosticTestResult.setPreviousScore(diagnosticTestResult.getScore());
        diagnosticTestResult.setDateTaken(LocalDateTime.now());
        diagnosticTestResult.setScore(score);
        return diagnosticTestResultRepository.save(diagnosticTestResult);
    }

    private Float getTestScore(List<CorrectDiagnosticQuestion> correctDiagnosticQuestions,
                               List<AnsweredQuestion> answeredQuestions) {

        long correctAnswersCount = correctDiagnosticQuestions.stream()
                .filter(correctQuestion -> answeredQuestions.stream()
                        .anyMatch(answeredQuestion ->
                                answeredQuestion.questionId().equals(correctQuestion.questionId()) &&
                                answeredQuestion.answer().equals(correctQuestion.correctAnswer())
                        )
                )
                .count();

        float totalQuestions = correctDiagnosticQuestions.size();
        return (totalQuestions > 0) ? (float) Math.round((correctAnswersCount / totalQuestions) * 100 * 100.0) / 100 : 0f;
    }

}
