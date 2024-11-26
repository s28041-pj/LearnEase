package pl.pjatk.learnease.repository.learn;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pjatk.learnease.entity.learn.Subject;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findSubjectByName(String name);
}
