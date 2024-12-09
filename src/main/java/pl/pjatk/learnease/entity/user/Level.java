package pl.pjatk.learnease.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "levels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Level {
    @Id
    @Column(name = "level_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long levelId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
