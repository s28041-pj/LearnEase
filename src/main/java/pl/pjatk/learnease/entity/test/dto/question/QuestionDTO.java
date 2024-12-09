package pl.pjatk.learnease.entity.test.dto.question;

public record QuestionDTO(Long questionId,
                          String questionText,
                          String answerA,
                          String answerB,
                          String answerC,
                          String answerD) {
}
