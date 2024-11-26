package pl.pjatk.learnease.entity.ranking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pjatk.learnease.entity.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "rankings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ranking {
    @Id
    @Column(name = "ranking_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rankingId;

    @Column(name = "rank", nullable = false)
    private Integer rank;

    @Column(name = "previous_rank")
    private Integer previousRank;

    @Column(name = "points", nullable = false)
    private Float points;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
