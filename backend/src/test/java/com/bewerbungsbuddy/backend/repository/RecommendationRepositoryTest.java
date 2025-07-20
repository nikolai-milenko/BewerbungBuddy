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
public class RecommendationRepositoryTest {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CVDocumentRepository cvDocumentRepository;

    @Autowired
    private JobAdvertisementRepository jobAdvertisementRepository;

    @Autowired
    private CVAnalysisRepository cvAnalysisRepository;

    @Test
    @DisplayName("findAllByCvAnalysis_Id – should return recommendations for analysis")
    void findAllByCvAnalysisId_shouldReturnCorrectRecommendations() {
        User user = userRepository.save(User.builder()
                .email("rec@test.com")
                .password("pass")
                .fullName("Rec User")
                .createdAt(LocalDateTime.now())
                .build());

        CVDocument cv = cvDocumentRepository.save(CVDocument.builder()
                .user(user)
                .filename("cv.pdf")
                .parsedText("text")
                .uploadedAt(LocalDateTime.now())
                .build());

        JobAdvertisement job = jobAdvertisementRepository.save(JobAdvertisement.builder()
                .user(user)
                .rawText("job")
                .companyName("Org")
                .jobTitle("Engineer")
                .build());

        CVAnalysis analysis = cvAnalysisRepository.save(CVAnalysis.builder()
                .cvDocument(cv)
                .jobAdvertisement(job)
                .matchScore(88.0)
                .strengths(List.of("Skill"))
                .weaknesses(List.of("Gap"))
                .build());

        Recommendation rec1 = Recommendation.builder()
                .text("Add keywords")
                .category(RecommendationCategory.ADD_KEYWORDS)
                .cvAnalysis(analysis)
                .build();

        Recommendation rec2 = Recommendation.builder()
                .text("Improve formatting")
                .category(RecommendationCategory.FORMAT_IMPROVEMENT)
                .cvAnalysis(analysis)
                .build();

        recommendationRepository.saveAll(List.of(rec1, rec2));

        List<Recommendation> result = recommendationRepository.findAllByCvAnalysis_Id(analysis.getId());

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Recommendation::getText)
                .containsExactlyInAnyOrder("Add keywords", "Improve formatting");
    }
}
