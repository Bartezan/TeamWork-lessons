package com.skypro.lessons.services;

import com.skypro.lessons.interfaces.RecommendationRuleSet;
import com.skypro.lessons.models.RecomendationDto;
import com.skypro.lessons.repositories.RecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationsService {
    @Autowired
    private List<RecommendationRuleSet> allRecommendation;

    public List<RecomendationDto> getRecommendationToUser(UUID user) {
        return allRecommendation.stream()
                .map(rec -> rec.getRecommendation(user))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
