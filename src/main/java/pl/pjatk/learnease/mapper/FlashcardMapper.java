package pl.pjatk.learnease.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import pl.pjatk.learnease.entity.learn.Flashcard;
import pl.pjatk.learnease.entity.learn.Subject;
import pl.pjatk.learnease.entity.learn.dto.FlashcardDTO;
import pl.pjatk.learnease.entity.learn.dto.RequestedFlashcard;

@Mapper
public interface FlashcardMapper {

    @Mapping(target = "subject", source = "subject", qualifiedByName = "getSubjectName")
    FlashcardDTO toFlashcardDTO(Flashcard flashcard);

    @Mapping(target = "flashcardId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Flashcard updateFlashcard(@MappingTarget Flashcard flashcard, RequestedFlashcard requestedFlashcard);

    @Named("getSubjectName")
    default String getSubjectName(Subject subject) {
        return (subject != null) ? subject.getName() : null;
    }

}
