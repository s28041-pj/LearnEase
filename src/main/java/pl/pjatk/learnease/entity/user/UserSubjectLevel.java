package pl.pjatk.learnease.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pjatk.learnease.entity.learn.Subject;

@Entity
@Table(name = "user_subject_levels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSubjectLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_subject_level_id", unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
}

