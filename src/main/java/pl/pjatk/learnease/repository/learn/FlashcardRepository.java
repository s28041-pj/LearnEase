package pl.pjatk.learnease.repository.learn;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pjatk.learnease.entity.learn.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}
