package pl.pjatk.learnease.entity.test.dto.test;

import pl.pjatk.learnease.entity.test.dto.question.QuestionDTO;

import java.util.List;

public record DiagnosticTestDTO(Long diagnosticTestId,
                                String subject,
                                String testName,
                                List<QuestionDTO> questions) {
}
