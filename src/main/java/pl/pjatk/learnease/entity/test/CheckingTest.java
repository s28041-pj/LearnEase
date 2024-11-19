package pl.pjatk.learnease.entity.test;

import jakarta.persistence.*;
import lombok.*;
import pl.pjatk.learnease.entity.learn.Subject;
import pl.pjatk.learnease.entity.user.User;

import java.time.LocalDate;

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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "test_date")
    private LocalDate testDate;

    private Float score;

    @Column(name = "previous_score")
    private Float previousScore;

    private boolean deleted;
}
