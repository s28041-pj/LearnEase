package pl.pjatk.learnease.entity.test;

import jakarta.persistence.*;
import lombok.*;
import pl.pjatk.learnease.entity.learn.Subject;
import pl.pjatk.learnease.entity.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "checking_tests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckingTest {
    @Id
    @Column(name = "checking_test_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer checkingTestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "test_date", nullable = false)
    private LocalDateTime testDate;

    @Column(name = "score", nullable = false)
    private Float score;

    @Column(name = "previous_score")
    private Float previousScore;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
