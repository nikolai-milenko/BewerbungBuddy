package com.bewerbungsbuddy.backend.repository;

import com.bewerbungsbuddy.backend.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CVAnalysisRepositoryTest {

    @Autowired
    private CVAnalysisRepository cvAnalysisRepository;

    @Autowired
    private CVDocumentRepository cvDocumentRepository;

    @Autowired
    private JobAdvertisementRepository jobAdvertisementRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findAllByCvDocument_Id — should return correct analyses")
    void findAllByCvDocumentId_shouldReturnResults() {
        User user = userRepository.save(User.builder()
                .email("cv@test.com")
                .password("pass")
                .fullName("CV User")
                .createdAt(LocalDateTime.now())
                .build());

        CVDocument cv = cvDocumentRepository.save(CVDocument.builder()
                .user(user)
                .filename("cv.pdf")
                .parsedText("parsed")
                .uploadedAt(LocalDateTime.now())
                .build());

        JobAdvertisement job = jobAdvertisementRepository.save(JobAdvertisement.builder()
                .user(user)
                .rawText("job description")
                .companyName("Company")
                .jobTitle("Engineer")
                .build());

        CVAnalysis analysis = CVAnalysis.builder()
                .cvDocument(cv)
                .jobAdvertisement(job)
                .matchScore(90.0)
                .strengths(List.of("Strength A"))
                .weaknesses(List.of("Weakness A"))
                .build();

        cvAnalysisRepository.save(analysis);

        List<CVAnalysis> result = cvAnalysisRepository.findAllByCvDocument_Id(cv.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCvDocument().getId()).isEqualTo(cv.getId());
    }

    @Test
    @DisplayName("findAllByJobAdvertisement_Id — should return correct analyses")
    void findAllByJobAdvertisementId_shouldReturnResults() {
        User user = userRepository.save(User.builder()
                .email("job@test.com")
                .password("pass")
                .fullName("Job User")
                .createdAt(LocalDateTime.now())
                .build());

        CVDocument cv = cvDocumentRepository.save(CVDocument.builder()
                .user(user)
                .filename("cv2.pdf")
                .parsedText("text")
                .uploadedAt(LocalDateTime.now())
                .build());

        JobAdvertisement job = jobAdvertisementRepository.save(JobAdvertisement.builder()
                .user(user)
                .rawText("raw text")
                .companyName("Biz")
                .jobTitle("Dev")
                .build());

        CVAnalysis analysis = CVAnalysis.builder()
                .cvDocument(cv)
                .jobAdvertisement(job)
                .matchScore(75.0)
                .strengths(List.of())
                .weaknesses(List.of())
                .build();

        cvAnalysisRepository.save(analysis);

        List<CVAnalysis> result = cvAnalysisRepository.findAllByJobAdvertisement_Id(job.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getJobAdvertisement().getId()).isEqualTo(job.getId());
    }

    
}
