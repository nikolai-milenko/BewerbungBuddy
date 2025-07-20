package com.bewerbungsbuddy.backend.repository;

import com.bewerbungsbuddy.backend.entity.CVDocument;
import com.bewerbungsbuddy.backend.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CVDocumentRepositoryTest {
    @Autowired
    private CVDocumentRepository cvDocumentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("save and findById – should persist and retrieve a CV document")
    void saveAndFindById_shouldWorkCorrectly() {
        User user = userRepository.save(User.builder()
                .email("cvdoc@test.com")
                .password("pass")
                .fullName("CV Doc User")
                .createdAt(LocalDateTime.now())
                .build());

        CVDocument cv = CVDocument.builder()
                .user(user)
                .filename("my_cv.pdf")
                .parsedText("Example parsed text")
                .uploadedAt(LocalDateTime.now())
                .build();

        CVDocument saved = cvDocumentRepository.save(cv);

        Optional<CVDocument> found = cvDocumentRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getFilename()).isEqualTo("my_cv.pdf");
        assertThat(found.get().getUser().getEmail()).isEqualTo("cvdoc@test.com");
    }
}
