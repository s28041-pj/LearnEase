package pl.pjatk.learnease.entity.test.dto.question;

public record RequestedQuestion(String questionText,
                                String answerA,
                                String answerB,
                                String answerC,
                                String answerD,
                                String correctAnswer) {
}
