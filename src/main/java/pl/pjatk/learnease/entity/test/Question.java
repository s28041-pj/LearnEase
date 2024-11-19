package pl.pjatk.learnease.entity.test;

import jakarta.persistence.*;
import lombok.*;

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
    private Integer questionId;

    @Column(name = "answer_a")
    private String answerA;

    @Column(name = "answer_b")
    private String answerB;

    @Column(name = "answer_c")
    private String answerC;

    @Column(name = "answer_d")
    private String answerD;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checking_test_id")
    private CheckingTest checkingTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnostic_test_id")
    private DiagnosticTest diagnosticTest;

    private boolean deleted;
}
