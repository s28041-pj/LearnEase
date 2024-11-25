package pl.pjatk.learnease.entity.learn;

import jakarta.persistence.*;
import lombok.*;
import pl.pjatk.learnease.entity.user.User;

@Entity
@Table(name = "flashcards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Flashcard {
    @Id
    @Column(name = "flashcard_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flashcardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "front_text", nullable = false)
    private String frontText;

    @Column(name = "back_text", nullable = false)
    private String backText;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
