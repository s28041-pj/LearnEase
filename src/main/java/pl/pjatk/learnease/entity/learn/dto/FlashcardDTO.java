package pl.pjatk.learnease.entity.learn.dto;

public record FlashcardDTO(Long flashcardId,
                           String subject,
                           String frontText,
                           String backText) {
}
