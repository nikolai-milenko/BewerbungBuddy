package com.bewerbungsbuddy.backend.repository;

import com.bewerbungsbuddy.backend.entity.CoverLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoverLetterRepository extends JpaRepository<CoverLetter, Long> {
    List<CoverLetter> findByUserId(Long userId);
    List<CoverLetter> findByCvDocumentId(Long cvDocumentId);
    List<CoverLetter> findByJobAdvertisementId(Long jobAdvertisementId);
}
