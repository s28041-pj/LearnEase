package pl.pjatk.learnease.entity.test;

import jakarta.persistence.*;
import lombok.*;
import pl.pjatk.learnease.entity.learn.Subject;
import pl.pjatk.learnease.entity.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "diagnostic_tests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiagnosticTest {
    @Id
    @Column(name = "diagnostic_test_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer diagnosticTestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "date_taken", nullable = false)
    private LocalDateTime dateTaken;

    @Column(name = "score", nullable = false)
    private Float score;

    @Column(name = "previous_score")
    private Float previousScore;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
