package pl.pjatk.learnease.entity.quiz;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pjatk.learnease.entity.test.Question;
import pl.pjatk.learnease.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "quiz_rooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_room_id", nullable = false)
    private Integer quizRoomId;

    private String name;

    @Column(name = "access_code", unique = true, nullable = false)
    private String accessCode;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "quiz_participants",
        joinColumns = @JoinColumn(name = "quiz_room_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_room_id")
    private List<Question> questions;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Enumerated(EnumType.STRING)
    private QuizStatus status;

    private boolean completed = false;

}
