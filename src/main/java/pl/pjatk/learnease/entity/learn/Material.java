package pl.pjatk.learnease.entity.learn;

import jakarta.persistence.*;
import lombok.*;
import pl.pjatk.learnease.entity.user.Level;

import java.time.LocalDate;

@Entity
@Table(name = "materials")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Material {
    @Id
    @Column(name = "material_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer materialId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private Level level;

    private String title;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @Column(name = "created_at")
    private LocalDate createdAt;

    private boolean completed;

    private boolean deleted;
}
