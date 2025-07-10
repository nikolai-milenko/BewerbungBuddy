package com.bewerbungsbuddy.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "cv_document")
public class CVDocument {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            name = "filename",
            length = 50,
            nullable = false
    )
    private String filename;

    @Column(
            name = "uploaded_at",
            updatable = false
    )
    private LocalDateTime uploadedAt;

    @Column(
            name = "parsed_text",
            nullable = false,
            updatable = false
    )
    private String parsedText;

    @OneToMany(mappedBy = "cvDocument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CVAnalysis> analyses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "cvDocument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoverLetter> coverLetters;

    @PrePersist
    public void prePersist() {
        this.uploadedAt = LocalDateTime.now();
    }
}
