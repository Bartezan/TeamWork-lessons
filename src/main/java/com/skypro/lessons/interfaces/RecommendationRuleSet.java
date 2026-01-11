package com.skypro.lessons.interfaces;

import com.skypro.lessons.models.RecomendationDto;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    public Optional<RecomendationDto> getRecommendation(UUID user);
}
