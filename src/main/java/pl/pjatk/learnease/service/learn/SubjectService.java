package pl.pjatk.learnease.service.learn;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjatk.learnease.configure.exception.BusinessException;
import pl.pjatk.learnease.entity.learn.Subject;
import pl.pjatk.learnease.repository.learn.SubjectRepository;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public Subject getSubjectByName(String subjectName) {
        return subjectRepository.findSubjectByName(subjectName)
                .orElseThrow(() -> new BusinessException("Cannot find subject with name: " + subjectName));
    }
}
