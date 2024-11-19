package pl.pjatk.learnease.entity.learn;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subjects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subject {
    @Id
    @Column(name = "subject_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subjectId;

    private String name;

    private String description;

    private boolean deleted;
}
