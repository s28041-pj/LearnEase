package pl.pjatk.learnease.entity.quiz;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pjatk.learnease.entity.test.Question;
import pl.pjatk.learnease.entity.user.User;

@Entity
@Table(name = "quiz_answers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_answer_id", nullable = false)
    private Integer quizAnswerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "selected_answer", nullable = false)
    private String selectedAnswer;

    @Column(name = "correct", nullable = false)
    private boolean correct;

}
