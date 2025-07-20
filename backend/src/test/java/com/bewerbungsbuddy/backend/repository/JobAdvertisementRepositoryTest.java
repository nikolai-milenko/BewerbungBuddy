package com.bewerbungsbuddy.backend.repository;

import com.bewerbungsbuddy.backend.entity.JobAdvertisement;
import com.bewerbungsbuddy.backend.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JobAdvertisementRepositoryTest {

    @Autowired
    private JobAdvertisementRepository jobAdvertisementRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findAllByUser – should return job ads for a specific user")
    void findAllByUser_shouldReturnCorrectJobAds() {
        User user1 = userRepository.save(User.builder()
                .email("user1@test.com")
                .password("pass")
                .fullName("User One")
                .createdAt(LocalDateTime.now())
                .build());

        User user2 = userRepository.save(User.builder()
                .email("user2@test.com")
                .password("pass")
                .fullName("User Two")
                .createdAt(LocalDateTime.now())
                .build());

        jobAdvertisementRepository.save(JobAdvertisement.builder()
                .user(user1)
                .rawText("text1")
                .jobTitle("Dev")
                .companyName("Company A")
                .build());

        jobAdvertisementRepository.save(JobAdvertisement.builder()
                .user(user2)
                .rawText("text2")
                .jobTitle("QA")
                .companyName("Company B")
                .build());

        List<JobAdvertisement> result = jobAdvertisementRepository.findAllByUser(user1);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getId()).isEqualTo(user1.getId());
        assertThat(result.get(0).getJobTitle()).isEqualTo("Dev");
    }
}
