package pl.pjatk.learnease.controller.learn;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.learnease.entity.learn.dto.RequestedFlashcard;
import pl.pjatk.learnease.service.learn.FlashcardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FlashcardController {
    private final FlashcardService flashcardService;

    @PostMapping("/flashcards")
    public void addFlashcards(@RequestBody List<RequestedFlashcard> flashcards) {
        flashcardService.addFlashcards(flashcards);
    }
}
