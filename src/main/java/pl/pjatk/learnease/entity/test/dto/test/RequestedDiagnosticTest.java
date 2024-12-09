package pl.pjatk.learnease.entity.test.dto.test;

import pl.pjatk.learnease.entity.test.dto.question.RequestedQuestion;

import java.util.List;

public record RequestedDiagnosticTest(String subject,
                                      String testName,
                                      List<RequestedQuestion> questions) {
}
