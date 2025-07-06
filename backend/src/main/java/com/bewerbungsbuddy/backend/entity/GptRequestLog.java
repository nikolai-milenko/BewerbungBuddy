package com.bewerbungsbuddy.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "gpt_request_log")
public class GptRequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestType type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String requestPayload;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String responsePayload;

    @Column(nullable = false, updatable = false)
    private Instant timestamp;

    @PrePersist
    public void prePersist() {
        this.timestamp = Instant.now();
    }
}
