package pl.pjatk.learnease.entity.user;

import jakarta.persistence.*;
import lombok.*;

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
    private Integer levelId;

    private String name;

    private String description;

    private boolean deleted;
}
