package pl.pjatk.learnease.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pjatk.learnease.configure.exception.BusinessException;
import pl.pjatk.learnease.entity.test.dto.test.RequestedDiagnosticTest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiagnosticTestValidator {

    public static void validateCreateDiagnosticTestQuestion(RequestedDiagnosticTest requestedDiagnosticTest) {
        if (requestedDiagnosticTest.questions().size() >= 20) {
            throw new BusinessException("You cannot create diagnostic test with more than 20 questions");
        }
    }
}
