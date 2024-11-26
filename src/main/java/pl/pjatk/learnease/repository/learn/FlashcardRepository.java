package pl.pjatk.learnease.repository.learn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.pjatk.learnease.entity.learn.Flashcard;

import java.util.List;
import java.util.Optional;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    @Query("""
           SELECT flashcard FROM Flashcard flashcard
           WHERE flashcard.user.userId = :userId
            AND flashcard.deleted = false
           """)
    List<Flashcard> findAllByUserUserId(Integer userId);

    @Query("""
           SELECT flashcard FROM Flashcard flashcard
           WHERE flashcard.user.userId = :userId
            AND flashcard.flashcardId = :flashcardId
            AND flashcard.deleted = false
           """)
    Optional<Flashcard> findByFlashcardIdAndUserUserId(Integer userId, Long flashcardId);

    @Query("""
           SELECT flashcard FROM Flashcard flashcard
           WHERE flashcard.user.userId = :userId
            AND flashcard.subject.name = :subjectName
            AND flashcard.deleted = false
           """)
    List<Flashcard> findAllByUserUserIdAndSubjectName(Integer userId, String subjectName);

    @Modifying
    @Query("""
           UPDATE Flashcard flashcard SET flashcard.deleted = true
           WHERE flashcard.user.userId = :userId
            AND flashcard.flashcardId = :flashcardId
           """)
    void deleteByFlashcardIdAndUserUserId(Long flashcardId, Integer userId);
}
