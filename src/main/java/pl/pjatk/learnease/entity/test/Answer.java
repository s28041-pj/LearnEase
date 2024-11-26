package pl.pjatk.learnease.entity.test;

import jakarta.persistence.*;
import lombok.*;
import pl.pjatk.learnease.entity.user.User;

@Entity
@Table(name = "answers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Answer {
    @Id
    @Column(name = "answer_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "user_answer", nullable = false)
    private String userAnswer;

    @Column(name = "correct", nullable = false)
    private boolean correct;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
