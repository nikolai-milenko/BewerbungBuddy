package com.bewerbungsbuddy.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import com.bewerbungsbuddy.backend.entity.SubscriptionType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "registered_at",nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    @Column(name = "subscription_plan")
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionPlan;

    @PrePersist
    public void onCreate() {
        this.registeredAt = LocalDateTime.now();
    }

    // TODO: entity CVDocument
    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<CVDocument> cvDocuments;

    // TODO: entity CoverLetter
    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<CoverLetter> coverLetters;
}
