package pl.pjatk.learnease.repository.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.pjatk.learnease.entity.test.DiagnosticTestResult;

import java.util.Optional;

public interface DiagnosticTestResultRepository extends JpaRepository<DiagnosticTestResult, Long> {

    @Query("""
           SELECT diagnosticTestResult FROM DiagnosticTestResult diagnosticTestResult
           WHERE diagnosticTestResult.user.username = :username
            AND diagnosticTestResult.testTemplate.testName = :testName
            AND diagnosticTestResult.deleted = false
           """)
    Optional<DiagnosticTestResult> findDiagnosticTestResultByUsernameAndSubject(String username, String testName);
}
