package pl.pjatk.learnease.service.test;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjatk.learnease.configure.exception.BusinessException;
import pl.pjatk.learnease.entity.learn.Subject;
import pl.pjatk.learnease.entity.test.DiagnosticTestTemplate;
import pl.pjatk.learnease.entity.test.Question;
import pl.pjatk.learnease.entity.test.dto.test.DiagnosticTestDTO;
import pl.pjatk.learnease.entity.test.dto.test.DiagnosticTestResultDTO;
import pl.pjatk.learnease.entity.test.dto.test.RequestedDiagnosticTest;
import pl.pjatk.learnease.entity.test.dto.test.ResolvedDiagnosticTest;
import pl.pjatk.learnease.mapper.DiagnosticTestMapper;
import pl.pjatk.learnease.repository.test.DiagnosticTestTemplateRepository;
import pl.pjatk.learnease.service.learn.SubjectService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.pjatk.learnease.service.DiagnosticTestValidator.validateCreateDiagnosticTestQuestion;

@Service
@RequiredArgsConstructor
public class DiagnosticTestService {

    private final SubjectService subjectService;
    private final QuestionService questionService;
    private final DiagnosticTestTemplateRepository diagnosticTestTemplateRepository;
    private final DiagnosticTestMapper diagnosticTestMapper;
    private final TestResolverService testResolverService;

    @Transactional
    public void createDiagnosticTest(RequestedDiagnosticTest diagnosticTest) {
        validateCreateDiagnosticTestQuestion(diagnosticTest);
        Optional<DiagnosticTestTemplate> diagnosticTestBySubject = diagnosticTestTemplateRepository.findDiagnosticTestBySubject(diagnosticTest.subject());
        if (diagnosticTestBySubject.isPresent()) {
            throw new BusinessException("Diagnostic test template already exists");
        }

        DiagnosticTestTemplate diagnosticTestTemplate = buildDiagnosticTestTemplate(diagnosticTest);
        List<Question> questions = buildQuestionsForDiagnosticTest(diagnosticTest, diagnosticTestTemplate);

        saveDiagnosticTest(diagnosticTestTemplate, questions);
    }

    public DiagnosticTestDTO getSubjectDiagnosticTest(String subject) {
        return diagnosticTestTemplateRepository.findDiagnosticTestBySubject(subject)
                .map(diagnosticTestMapper::toDiagnosticTestDTO)
                .orElseThrow(() -> new BusinessException("Cannot find diagnostic test for subject " + subject));
    }

    public DiagnosticTestResultDTO solveSubjectDiagnosticTest(String subject, Long diagnosticTestId, ResolvedDiagnosticTest resolvedDiagnosticTest) {
        Optional<DiagnosticTestTemplate> foundDiagnosticTest = diagnosticTestTemplateRepository.findById(diagnosticTestId);
        if (foundDiagnosticTest.isPresent()) {
            return testResolverService.solveDiagnosticTest(foundDiagnosticTest.get(), resolvedDiagnosticTest);
        }
        throw new BusinessException("Cannot find diagnostic test for subject " + subject);
    }

    public DiagnosticTestResultDTO correctSubjectDiagnosticTest(String subject, Long diagnosticTestId, ResolvedDiagnosticTest resolvedDiagnosticTest) {
        Optional<DiagnosticTestTemplate> foundDiagnosticTest = diagnosticTestTemplateRepository.findById(diagnosticTestId);
        if (foundDiagnosticTest.isPresent()) {
            return testResolverService.resolveDiagnosticTest(foundDiagnosticTest.get(), resolvedDiagnosticTest);
        }
        throw new BusinessException("Cannot find diagnostic test for subject " + subject);
    }

    @Transactional
    public void deleteDiagnosticTest(Long diagnosticTestId) {
        diagnosticTestTemplateRepository.deleteDiagnosticTestById(diagnosticTestId);
    }

    private DiagnosticTestTemplate buildDiagnosticTestTemplate(RequestedDiagnosticTest requestedDiagnosticTest) {
        Subject subject = subjectService.getSubjectByName(requestedDiagnosticTest.subject());

        return new DiagnosticTestTemplate(
                null,
                subject,
                requestedDiagnosticTest.testName(),
                null,
                false
        );
    }

    private List<Question> buildQuestionsForDiagnosticTest(RequestedDiagnosticTest diagnosticTest,
                                                           DiagnosticTestTemplate diagnosticTestTemplate) {
        return diagnosticTest.questions().stream()
                .map(question -> questionService.createQuestionFromRequest(question, diagnosticTestTemplate))
                .collect(Collectors.toList());
    }

    private void saveDiagnosticTest(DiagnosticTestTemplate diagnosticTestTemplate, List<Question> questions) {
        diagnosticTestTemplate.setQuestions(questions);
        diagnosticTestTemplateRepository.save(diagnosticTestTemplate);
    }
}
