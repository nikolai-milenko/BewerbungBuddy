package com.bewerbungsbuddy.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "cover_letter")
public class CoverLetter {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(name = "created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "generated_text", columnDefinition = "TEXT")
    private String generatedText;

    @Column(name = "edited_text", columnDefinition = "TEXT")
    private String editedText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_document_id")
    private CVDocument cvDocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_advertisement_id")
    private JobAdvertisement jobAdvertisement;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
