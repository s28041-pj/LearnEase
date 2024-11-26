package pl.pjatk.learnease.entity.ranking;

import jakarta.persistence.*;
import lombok.*;
import pl.pjatk.learnease.entity.learn.Material;
import pl.pjatk.learnease.entity.test.CheckingTest;
import pl.pjatk.learnease.entity.user.User;

@Entity
@Table(name = "points")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Point {
    @Id
    @Column(name = "point_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checking_test_id")
    private CheckingTest checkingTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
