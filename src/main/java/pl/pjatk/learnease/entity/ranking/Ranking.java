package pl.pjatk.learnease.entity.ranking;

import jakarta.persistence.*;
import lombok.*;
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

    private Integer rank;

    @Column(name = "previous_rank")
    private Integer previousRank;

    private Float points;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
}
