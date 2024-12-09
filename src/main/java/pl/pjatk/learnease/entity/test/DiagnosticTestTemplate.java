package pl.pjatk.learnease.entity.test;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pjatk.learnease.entity.learn.Subject;

import java.util.List;

@Entity
@Table(name = "diagnostic_test_templates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiagnosticTestTemplate {
    @Id
    @Column(name = "diagnostic_test_template_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diagnosticTestTemplateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "test_name", nullable = false)
    private String testName;

    @OneToMany(mappedBy = "diagnosticTestTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
