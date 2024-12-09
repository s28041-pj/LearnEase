package pl.pjatk.learnease.entity.test.dto.test;

import java.time.LocalDateTime;

public record DiagnosticTestResultDTO(Float score,
                                      Float previousScore,
                                      LocalDateTime dateTaken,
                                      String testName) {
}
