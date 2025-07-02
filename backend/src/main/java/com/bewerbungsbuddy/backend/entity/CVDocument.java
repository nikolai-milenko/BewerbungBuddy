package com.bewerbungsbuddy.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
            name = "uploaded_at"
    )
    private LocalDateTime uploadedAt;

    @Column(
            name = "parsed_text",
            nullable = false,
            updatable = false
    )
    private String parsedText;

    @PrePersist
    public void prePersist() {
        this.uploadedAt = LocalDateTime.now();
    }
}
