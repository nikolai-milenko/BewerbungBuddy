package com.bewerbungsbuddy.backend.repository;

import com.bewerbungsbuddy.backend.entity.CVAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CVAnalysisRepository extends JpaRepository<CVAnalysis, Long> {
    List<CVAnalysis> findAllByCvDocument_Id(Long cvDocumentId);

    List<CVAnalysis> findAllByJobAdvertisement_Id(Long jobAdvertisementId);
}
