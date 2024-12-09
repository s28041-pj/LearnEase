package pl.pjatk.learnease.entity.test;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pjatk.learnease.entity.quiz.QuizRoom;

@Entity
@Table(name = "questions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question {
    @Id
    @Column(name = "question_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "answer_a", nullable = false)
    private String answerA;

    @Column(name = "answer_b", nullable = false)
    private String answerB;

    @Column(name = "answer_c", nullable = false)
    private String answerC;

    @Column(name = "answer_d", nullable = false)
    private String answerD;

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checking_test_id")
    private CheckingTest checkingTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnostic_test_template_id")
    private DiagnosticTestTemplate diagnosticTestTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_room_id")
    private QuizRoom quizRoom;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
