package pl.pjatk.learnease.entity.test.dto.test;

import pl.pjatk.learnease.entity.test.dto.question.AnsweredQuestion;

import java.util.List;

public record ResolvedDiagnosticTest(List<AnsweredQuestion> answeredQuestions) {
}
