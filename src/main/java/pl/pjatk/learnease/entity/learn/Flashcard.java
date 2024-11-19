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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "front_text")
    private String frontText;

    @Column(name = "back_text")
    private String backText;

    private boolean deleted;
}
