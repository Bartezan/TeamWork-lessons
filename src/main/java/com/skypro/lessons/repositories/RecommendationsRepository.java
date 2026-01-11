package com.skypro.lessons.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getIntFromCustomRequest(String request) {
        int result;
        try {
            result = jdbcTemplate.queryForObject(
                    request,
                    Integer.class
            );
            return result;
        } catch (NullPointerException e) {
            return result = 0;
        }
    }
}
