package pl.pjatk.learnease.controller.learn;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.learnease.entity.learn.dto.FlashcardDTO;
import pl.pjatk.learnease.entity.learn.dto.RequestedFlashcard;
import pl.pjatk.learnease.service.learn.FlashcardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FlashcardController {
    private final FlashcardService flashcardService;

    @Operation(description = "Create flashcards for logged user")
    @PostMapping("/flashcards")
    public ResponseEntity<Void> addFlashcards(@RequestBody List<RequestedFlashcard> flashcards) {
        flashcardService.addFlashcards(flashcards);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Returns all flashcards created by logged user")
    @GetMapping("/flashcards")
    public ResponseEntity<List<FlashcardDTO>> getAllFlashcards() {
        return ResponseEntity.ok(flashcardService.getAllFlashcards());
    }

    @Operation(description = "Returns all flashcards for provided subject" +
                             "for logged user")
    @GetMapping("/flashcards/{subject}")
    public ResponseEntity<List<FlashcardDTO>> getAllFlashcardsBySubject(@PathVariable String subject) {
        return ResponseEntity.ok(flashcardService.getAllFlashcardsBySubject(subject));
    }

    @Operation(description = "Update flashcard for provided id for logged user")
    @PatchMapping("/flashcards/{flashcardId}")
    public ResponseEntity<FlashcardDTO> updateFlashcard(@PathVariable Long flashcardId, @RequestBody RequestedFlashcard requestedFlashcard) {
        return ResponseEntity.ok(flashcardService.updateFlashcard(flashcardId, requestedFlashcard));
    }

    @Operation(description = "Delete flashcard for provided id for logged user")
    @DeleteMapping("/flashcards/{flashcardId}")
    public ResponseEntity<Void> deleteFlashcard(@PathVariable Long flashcardId) {
        flashcardService.deleteFlashcard(flashcardId);
        return ResponseEntity.ok().build();
    }

}
