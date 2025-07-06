package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.RecommendationResponseDto;
import com.bewerbungsbuddy.backend.entity.Recommendation;
import com.bewerbungsbuddy.backend.entity.RecommendationCategory;
import com.bewerbungsbuddy.backend.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;
    // TODO: add gpt call
    @PostMapping("/{cvAnalysisId}")
    public ResponseEntity<Void> addRecommendation(@PathVariable Long cvAnalysisId, @RequestBody String text) {
        Recommendation recommendation = Recommendation.builder()
                .text(text)
                .category(RecommendationCategory.ADD_KEYWORDS) // just for now
                .build();

        recommendationService.addRecommendationToAnalysis(cvAnalysisId, recommendation);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cvAnalysisId}")
    public ResponseEntity<List<RecommendationResponseDto>> getRecommendations(@PathVariable Long cvAnalysisId) {
        return ResponseEntity.ok(recommendationService.getRecommendationsByAnalysisId(cvAnalysisId));
    }
}
