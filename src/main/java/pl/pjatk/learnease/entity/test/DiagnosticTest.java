package pl.pjatk.learnease.entity.test;

import jakarta.persistence.*;
import lombok.*;
import pl.pjatk.learnease.entity.learn.Subject;
import pl.pjatk.learnease.entity.user.User;

import java.time.LocalDate;

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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "date_taken")
    private LocalDate dateTaken;

    private Float score;

    @Column(name = "previous_score")
    private Float previousScore;

    private boolean deleted;
}
