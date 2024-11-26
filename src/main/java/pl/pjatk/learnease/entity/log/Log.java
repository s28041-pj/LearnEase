package pl.pjatk.learnease.entity.log;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pjatk.learnease.entity.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Log {
    @Id
    @Column(name = "log_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "log_date", nullable = false)
    private LocalDateTime logDate;

    @Column(name = "content", nullable = false)
    private String content;
}
