package com.bewerbungsbuddy.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
@Entity
@Table(name = "cv_analysis")
public class CVAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "match_score")
    private Double matchScore;

    /**
     * List of strengths extracted during the CV analysis.
     * <p>
     * Stored in a separate table using {@link ElementCollection} because JPA does not support
     * persisting collections of basic types directly in the main entity table.
     */
    @ElementCollection
    @CollectionTable(name = "cv_analysis_strengths", joinColumns = @JoinColumn(name = "cv_analysis_id"))
    @Column(name = "strengths")
    private List<String> strengths;


    /**
     * List of weaknesses extracted during the CV analysis.
     * <p>
     * Stored in a separate table using {@link ElementCollection} because JPA does not support
     * persisting collections of basic types directly in the main entity table.
     */
    @ElementCollection
    @CollectionTable(name = "cv_analysis_weaknesses", joinColumns = @JoinColumn(name = "cv_analysis_id"))
    @Column(name = "weaknesses")
    private List<String> weaknesses;

    @Column(name = "analysed_at", nullable = false, updatable = false)
    private LocalDateTime analysedAt;

    // connections to other entities

    // 0...* <- 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_document_id", nullable = false)
    private CVDocument cvDocument;

    // 0...* <- 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_advertisement_id", nullable = false)
    private JobAdvertisement jobAdvertisement;

    @PrePersist
    public void prePersist() {
        this.analysedAt = LocalDateTime.now();
    }
}
