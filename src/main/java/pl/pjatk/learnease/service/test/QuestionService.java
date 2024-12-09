package pl.pjatk.learnease.service.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjatk.learnease.entity.test.DiagnosticTestTemplate;
import pl.pjatk.learnease.entity.test.Question;
import pl.pjatk.learnease.entity.test.dto.question.CorrectDiagnosticQuestion;
import pl.pjatk.learnease.entity.test.dto.question.RequestedQuestion;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    public Question createQuestionFromRequest(RequestedQuestion requestedQuestion,
                                               DiagnosticTestTemplate diagnosticTestFromRequest) {
        Question question = new Question();
        question.setQuestionText(requestedQuestion.questionText());
        question.setAnswerA(requestedQuestion.answerA());
        question.setAnswerB(requestedQuestion.answerB());
        question.setAnswerC(requestedQuestion.answerC());
        question.setAnswerD(requestedQuestion.answerD());
        question.setCorrectAnswer(requestedQuestion.correctAnswer());
        question.setDiagnosticTestTemplate(diagnosticTestFromRequest);
        question.setDeleted(false);
        return question;
    }

    public List<CorrectDiagnosticQuestion> getCorrectDiagnosticQuestions(DiagnosticTestTemplate foundDiagnosticTest) {
        return foundDiagnosticTest.getQuestions().stream()
                .map(question ->
                        new CorrectDiagnosticQuestion(question.getQuestionId(), question.getCorrectAnswer()))
                .collect(Collectors.toList());
    }

}
