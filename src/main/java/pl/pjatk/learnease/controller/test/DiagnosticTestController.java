package pl.pjatk.learnease.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.learnease.entity.test.dto.test.DiagnosticTestDTO;
import pl.pjatk.learnease.entity.test.dto.test.DiagnosticTestResultDTO;
import pl.pjatk.learnease.entity.test.dto.test.RequestedDiagnosticTest;
import pl.pjatk.learnease.entity.test.dto.test.ResolvedDiagnosticTest;
import pl.pjatk.learnease.service.test.DiagnosticTestService;

@RestController
@RequiredArgsConstructor
public class DiagnosticTestController {

    private final DiagnosticTestService diagnosticTestService;

    @PostMapping("/diagnostic-test/create")
    public ResponseEntity<Void> createDiagnosticTest(@RequestBody RequestedDiagnosticTest diagnosticTest) {
        diagnosticTestService.createDiagnosticTest(diagnosticTest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/diagnostic-test/{subject}")
    public ResponseEntity<DiagnosticTestDTO> getSubjectDiagnosticTest(@PathVariable String subject) {
        return ResponseEntity.ok(diagnosticTestService.getSubjectDiagnosticTest(subject));
    }

    @PostMapping("/diagnostic-test/{subject}")
    public ResponseEntity<DiagnosticTestResultDTO> solveSubjectDiagnosticTest(@PathVariable String subject,
                                                                              @RequestParam Long diagnosticTestId,
                                                                              @RequestBody ResolvedDiagnosticTest resolvedDiagnosticTest) {
        return ResponseEntity.ok(diagnosticTestService.solveSubjectDiagnosticTest(subject, diagnosticTestId, resolvedDiagnosticTest));
    }

    @PutMapping("/diagnostic-test/{subject}")
    public ResponseEntity<DiagnosticTestResultDTO> correctSubjectDiagnosticTest(@PathVariable String subject,
                                                                                @RequestParam Long diagnosticTestId,
                                                                                @RequestBody ResolvedDiagnosticTest resolvedDiagnosticTest) {
        return ResponseEntity.ok(diagnosticTestService.correctSubjectDiagnosticTest(subject, diagnosticTestId, resolvedDiagnosticTest));
    }

    @DeleteMapping("/diagnostic-test/{testId}")
    public ResponseEntity<Void> deleteDiagnosticTest(@PathVariable Long testId) {
        diagnosticTestService.deleteDiagnosticTest(testId);
        return ResponseEntity.ok().build();
    }

}
