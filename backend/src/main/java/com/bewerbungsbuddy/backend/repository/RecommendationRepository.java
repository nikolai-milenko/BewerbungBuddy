package com.bewerbungsbuddy.backend.repository;

import com.bewerbungsbuddy.backend.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    List<Recommendation> findAllByCvAnalysis_Id(Long cvAnalysisId);
}
