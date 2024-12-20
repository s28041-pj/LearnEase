package pl.pjatk.learnease.entity.quiz;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pjatk.learnease.entity.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_participants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id", nullable = false)
    private Long participantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_room_id", nullable = false)
    private QuizRoom quizRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "score", nullable = false)
    private Float score;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "completed", nullable = false)
    private boolean completed;

}
