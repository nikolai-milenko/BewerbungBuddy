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
@Table(name = "сoverLetter")
public class CoverLetter {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(name = "created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "generated_text")
    private String generatedText;

    @Column(name = "edited_text")
    private String editedText;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private CVDocument cvDocument;

    @ManyToOne(fetch = FetchType.LAZY)
    private JobAdvertisement jobAdvertisement;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
