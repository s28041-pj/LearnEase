package pl.pjatk.learnease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pjatk.learnease.entity.user.Level;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {

    Optional<Level> findByName(String name);
}
