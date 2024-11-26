package pl.pjatk.learnease.service.learn;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjatk.learnease.configure.exception.BusinessException;
import pl.pjatk.learnease.entity.learn.Flashcard;
import pl.pjatk.learnease.entity.learn.Subject;
import pl.pjatk.learnease.entity.learn.dto.FlashcardDTO;
import pl.pjatk.learnease.entity.learn.dto.RequestedFlashcard;
import pl.pjatk.learnease.entity.user.User;
import pl.pjatk.learnease.mapper.FlashcardMapper;
import pl.pjatk.learnease.repository.learn.FlashcardRepository;

import java.util.List;

import static pl.pjatk.learnease.configure.UserContextHolder.getContextUser;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final SubjectService subjectService;
    private final FlashcardMapper flashcardMapper;
    private final FlashcardRepository flashcardRepository;

    public void addFlashcards(List<RequestedFlashcard> flashcards) {
        User user = getContextUser();
        List<Flashcard> createdFlashcards = mapToFlashcards(flashcards, user);
        flashcardRepository.saveAll(createdFlashcards);
    }

    public List<FlashcardDTO> getAllFlashcards() {
        return flashcardRepository.findAllByUserUserId(getContextUserId()).stream()
                .map(flashcardMapper::toFlashcardDTO)
                .toList();
    }

    public List<FlashcardDTO> getAllFlashcardsBySubject(String subject) {
        return flashcardRepository.findAllByUserUserIdAndSubjectName(getContextUserId(), subject).stream()
                .map(flashcardMapper::toFlashcardDTO)
                .toList();
    }

    @Transactional
    public FlashcardDTO updateFlashcard(Long flashcardId, RequestedFlashcard requestedFlashcard) {
        Flashcard flashcard = findFlashcardByIdAndUser(flashcardId);
        Subject subject = subjectService.getSubjectByName(requestedFlashcard.subject());
        return flashcardMapper.toFlashcardDTO(updateFlashcardDetails(flashcard, requestedFlashcard, subject));
    }

    @Transactional
    public void deleteFlashcard(Long flashcardId) {
        flashcardRepository.deleteByFlashcardIdAndUserUserId(flashcardId, getContextUserId());
    }

    private List<Flashcard> mapToFlashcards(List<RequestedFlashcard> flashcards, User user) {
        return flashcards.stream()
                .map(dto -> createFlashcard(dto, subjectService.getSubjectByName(dto.subject()), user))
                .toList();
    }

    private Flashcard findFlashcardByIdAndUser(Long flashcardId) {
        return flashcardRepository.findByFlashcardIdAndUserUserId(getContextUserId(), flashcardId)
                .orElseThrow(() -> new BusinessException("Flashcard not found for the given ID"));
    }

    private Flashcard updateFlashcardDetails(Flashcard flashcard, RequestedFlashcard requestedFlashcard, Subject subject) {
        flashcardMapper.updateFlashcard(flashcard, requestedFlashcard);
        flashcard.setSubject(subject);
        return flashcardRepository.save(flashcard);
    }

    private Flashcard createFlashcard(RequestedFlashcard requestedFlashcard, Subject subject, User user) {
        return new Flashcard(null, user, subject, requestedFlashcard.frontText(), requestedFlashcard.backText(), false);
    }

    private Integer getContextUserId() {
        return getContextUser().getUserId();
    }

}
