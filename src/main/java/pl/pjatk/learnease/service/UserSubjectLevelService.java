package pl.pjatk.learnease.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjatk.learnease.configure.exception.BusinessException;
import pl.pjatk.learnease.entity.test.DiagnosticTestResult;
import pl.pjatk.learnease.entity.user.Level;
import pl.pjatk.learnease.entity.user.User;
import pl.pjatk.learnease.entity.user.UserSubjectLevel;
import pl.pjatk.learnease.entity.user.dto.LevelName;
import pl.pjatk.learnease.repository.LevelRepository;
import pl.pjatk.learnease.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserSubjectLevelService {
    private final UserRepository userRepository;
    private final LevelRepository levelRepository;

    public void createOrUpdateUserSubjectLevel(DiagnosticTestResult diagnosticTestResult) {
        userRepository.findById(diagnosticTestResult.getUser().getUserId())
                .ifPresent(user -> {
                    UserSubjectLevel subjectLevel = findOrCreateUserSubjectLevel(user, diagnosticTestResult);
                    subjectLevel.setLevel(determineLevel(diagnosticTestResult.getScore()));
                    userRepository.save(user);
                });
    }

    private UserSubjectLevel findOrCreateUserSubjectLevel(User user, DiagnosticTestResult diagnosticTestResult) {
        return user.getUserSubjectLevels().stream()
                .filter(level -> level.getSubject()
                        .getName()
                        .equalsIgnoreCase(diagnosticTestResult.getTestTemplate().getSubject().getName()))
                .findFirst()
                .orElseGet(() -> createNewUserSubjectLevel(user, diagnosticTestResult));
    }

    private UserSubjectLevel createNewUserSubjectLevel(User user, DiagnosticTestResult diagnosticTestResult) {
        UserSubjectLevel newUserSubjectLevel = new UserSubjectLevel();
        newUserSubjectLevel.setSubject(diagnosticTestResult.getTestTemplate().getSubject());
        newUserSubjectLevel.setUser(user);
        user.getUserSubjectLevels().add(newUserSubjectLevel);
        return newUserSubjectLevel;
    }

    private Level determineLevel(Float score) {
        if (score <= 40) {
            return findLevelByName(LevelName.BEGINNER);
        } else if (score <= 70) {
            return findLevelByName(LevelName.INTERMEDIATE);
        } else {
            return findLevelByName(LevelName.ADVANCED);
        }
    }

    private Level findLevelByName(String levelName) {
        return levelRepository.findByName(levelName)
                .orElseThrow(() -> new BusinessException("Cannot find level: " + levelName));
    }

}
