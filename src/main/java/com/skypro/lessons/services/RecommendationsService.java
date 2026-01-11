package com.skypro.lessons.services;

import com.skypro.lessons.repositories.RecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecommendationsService {
    @Autowired
    private RecommendationsRepository repository;

    public RecommendationsService(RecommendationsRepository repository) {
        this.repository = repository;
    }

    public int getRecommendationToUser(UUID user) {
        return repository.getRandomTransactionAmount(user);
    }
}
