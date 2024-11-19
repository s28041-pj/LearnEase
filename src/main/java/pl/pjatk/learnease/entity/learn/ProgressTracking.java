package pl.pjatk.learnease.entity.learn;

import jakarta.persistence.*;
import lombok.*;
import pl.pjatk.learnease.entity.test.CheckingTest;
import pl.pjatk.learnease.entity.test.DiagnosticTest;
import pl.pjatk.learnease.entity.user.User;

import java.time.LocalDate;

@Entity
@Table(name = "progress_trackings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProgressTracking {
    @Id
    @Column(name = "progress_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer progressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checking_test_id")
    private CheckingTest checkingTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnostic_test_id")
    private DiagnosticTest diagnosticTest;

    @Column(name = "progress_value")
    private Float progressValue;

    @Column(name = "updated_at")
    private LocalDate updateAt;
}
