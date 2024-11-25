package pl.pjatk.learnease.service.learn;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjatk.learnease.entity.learn.Flashcard;
import pl.pjatk.learnease.entity.learn.Subject;
import pl.pjatk.learnease.entity.learn.dto.RequestedFlashcard;
import pl.pjatk.learnease.entity.user.User;
import pl.pjatk.learnease.repository.learn.FlashcardRepository;

import java.util.List;

import static pl.pjatk.learnease.configure.UserContextHolder.getContextUser;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final SubjectService subjectService;
    private final FlashcardRepository flashcardRepository;

    public void addFlashcards(List<RequestedFlashcard> flashcards) {
        User contextUser = getContextUser();
        List<Flashcard> createdFlashcards = flashcards.stream()
                .map(flashcard -> createFlashcard(
                        flashcard,
                        subjectService.getSubjectByName(flashcard.subject()),
                        contextUser
                ))
                .toList();

        flashcardRepository.saveAll(createdFlashcards);
    }

    private Flashcard createFlashcard(RequestedFlashcard flashcard,
                                      Subject subject,
                                      User user) {
        return new Flashcard(
                null,
                user,
                subject,
                flashcard.frontText(),
                flashcard.backText(),
                false
        );
    }

}
