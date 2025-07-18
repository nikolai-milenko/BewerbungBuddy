package com.bewerbungsbuddy.backend.repository;

import com.bewerbungsbuddy.backend.entity.CoverLetter;
import com.bewerbungsbuddy.backend.entity.User;
import com.bewerbungsbuddy.backend.entity.JobAdvertisement;
import com.bewerbungsbuddy.backend.entity.CVDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CoverLetterRepositoryTest {

    @Autowired
    private CoverLetterRepository coverLetterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobAdvertisementRepository jobAdvertisementRepository;

    @Autowired
    private CVDocumentRepository cvDocumentRepository;

    @Test
    @DisplayName("findByUserId – sollte Briefe für einen User liefern")
    void findByUserId_shouldReturnLetters() {
        User user = userRepository.save(User.builder()
                .email("test@test.com")
                .password("123")
                .fullName("Test User")
                .createdAt(LocalDateTime.now())
                .build());

        CoverLetter cl = CoverLetter.builder()
                .user(user)
                .generatedText("gen")
                .editedText("edit")
                .build();
        coverLetterRepository.save(cl);

        List<CoverLetter> result = coverLetterRepository.findByUserId(user.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("findByCvDocumentId – sollte Briefe für ein CV liefern")
    void findByCvDocumentId_shouldReturnLetters() {
        User user = userRepository.save(User.builder()
                .email("cv@test.com")
                .password("123")
                .fullName("Cv User")
                .createdAt(LocalDateTime.now())
                .build());

        CVDocument cv = cvDocumentRepository.save(CVDocument.builder()
                .user(user)
                .filename("file.pdf")
                .uploadedAt(LocalDateTime.now())
                .parsedText("text")
                .build());

        CoverLetter cl = CoverLetter.builder()
                .user(user)
                .cvDocument(cv)
                .generatedText("gen2")
                .editedText("edit2")
                .build();
        coverLetterRepository.save(cl);

        List<CoverLetter> result = coverLetterRepository.findByCvDocumentId(cv.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCvDocument().getId()).isEqualTo(cv.getId());
    }
}
