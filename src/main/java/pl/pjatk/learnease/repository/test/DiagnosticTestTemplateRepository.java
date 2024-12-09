package pl.pjatk.learnease.repository.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.pjatk.learnease.entity.test.DiagnosticTestTemplate;

import java.util.Optional;

public interface DiagnosticTestTemplateRepository extends JpaRepository<DiagnosticTestTemplate, Long> {

    @Query("""
           SELECT diagnosticTestTemplate FROM DiagnosticTestTemplate diagnosticTestTemplate
           WHERE diagnosticTestTemplate.subject.name = :subjectName
            AND diagnosticTestTemplate.deleted = false
           """)
    Optional<DiagnosticTestTemplate> findDiagnosticTestBySubject(String subjectName);

    @Modifying
    @Query("""
           UPDATE DiagnosticTestTemplate diagnosticTestTemplate
           SET diagnosticTestTemplate.deleted = true
           WHERE diagnosticTestTemplate.diagnosticTestTemplateId =:diagnosticTestId
           """)
    void deleteDiagnosticTestById(Long diagnosticTestId);
}
