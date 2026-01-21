package com.skypro.lessons.controllers;

import com.skypro.lessons.models.ResponsRecommendationDto;
import com.skypro.lessons.services.RecommendationsService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationsService recommendationsService;

    public RecommendationController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }

    @GetMapping("/{userId}")
    public ResponsRecommendationDto getRecommendationToUser(@PathVariable UUID userId) {
        ResponsRecommendationDto result = new ResponsRecommendationDto(userId, recommendationsService.getRecommendationToUser(userId));
        return result;
    }

}
