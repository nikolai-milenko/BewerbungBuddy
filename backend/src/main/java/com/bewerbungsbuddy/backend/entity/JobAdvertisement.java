package com.bewerbungsbuddy.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "job_advertisement")
public class JobAdvertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(
            name = "raw_text",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String rawText;

    @Column(
            name = "job_title",
            length = 255
    )
    private String jobTitle;

    @Column(
            name = "company_name",
            length = 100
    )
    private String companyName;

    @OneToMany(mappedBy = "jobAdvertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CVAnalysis> analyses;
}
