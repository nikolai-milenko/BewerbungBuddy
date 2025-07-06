package com.bewerbungsbuddy.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "recommendation")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecommendationCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_analysis_id", nullable = false)
    private CVAnalysis cvAnalysis;
}
