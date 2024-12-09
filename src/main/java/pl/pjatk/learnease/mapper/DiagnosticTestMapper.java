package pl.pjatk.learnease.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.pjatk.learnease.entity.learn.Subject;
import pl.pjatk.learnease.entity.test.DiagnosticTestResult;
import pl.pjatk.learnease.entity.test.DiagnosticTestTemplate;
import pl.pjatk.learnease.entity.test.dto.test.DiagnosticTestDTO;
import pl.pjatk.learnease.entity.test.dto.test.DiagnosticTestResultDTO;

@Mapper
public interface DiagnosticTestMapper {

    @Mapping(target = "subject", source = "subject", qualifiedByName = "getSubjectName")
    @Mapping(target = "diagnosticTestId", source = "diagnosticTestTemplateId")
    DiagnosticTestDTO toDiagnosticTestDTO(DiagnosticTestTemplate diagnosticTestTemplate);

    @Mapping(target = "testName", source = "testTemplate.testName")
    DiagnosticTestResultDTO toDiagnosticTestResultDTO(DiagnosticTestResult diagnosticTestResult);

    @Named("getSubjectName")
    default String getSubjectName(Subject subject) {
        return (subject != null) ? subject.getName() : null;
    }
}
