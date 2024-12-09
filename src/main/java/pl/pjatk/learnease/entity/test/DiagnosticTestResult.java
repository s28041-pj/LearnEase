package pl.pjatk.learnease.entity.test;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pjatk.learnease.entity.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "diagnostic_test_results")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiagnosticTestResult {
    @Id
    @Column(name = "test_result_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testResultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_template_id", nullable = false)
    private DiagnosticTestTemplate testTemplate;

    @Column(name = "date_taken", nullable = false)
    private LocalDateTime dateTaken;

    @Column(name = "score", nullable = false)
    private Float score;

    @Column(name = "previous_score")
    private Float previousScore;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
