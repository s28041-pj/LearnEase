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
    private Integer participantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_room_id")
    private QuizRoom quizRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "score")
    private Float score;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    private boolean completed;

}
