package com.skypro.lessons.interfaces.impl;

import com.skypro.lessons.interfaces.RecommendationRuleSet;
import com.skypro.lessons.models.RecomendationDto;
import com.skypro.lessons.repositories.RecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ImplInvestRecommendation implements RecommendationRuleSet {
    @Autowired
    private RecommendationsRepository repository;

    @Override
    public Optional<RecomendationDto> getRecommendation(UUID user) {
        int countDebit = repository.getIntFromCustomRequest("""
                SELECT COUNT(*)
                FROM TRANSACTIONS AS t
                JOIN USERS u ON t.USER_ID =u.ID
                JOIN PRODUCTS p ON t.PRODUCT_ID =p.ID""" +
                " WHERE t.USER_ID ='" + user + "' AND p.TYPE = 'DEBIT'");
        int countInvest = repository.getIntFromCustomRequest("""
                SELECT COUNT(*)
                FROM TRANSACTIONS AS t
                JOIN USERS u ON t.USER_ID =u.ID
                JOIN PRODUCTS p ON t.PRODUCT_ID =p.ID""" +
                " WHERE t.USER_ID ='" + user + "' AND p.TYPE = 'INVEST'");
        int sumSavingDeposit = repository.getIntFromCustomRequest("""
                SELECT  SUM(t.AMOUNT)
                FROM TRANSACTIONS AS t
                JOIN USERS u ON t.USER_ID =u.ID
                JOIN PRODUCTS p ON t.PRODUCT_ID =p.ID""" +
                " WHERE t.USER_ID ='" + user + "' AND p.TYPE = 'SAVING' AND t.TYPE = 'DEPOSIT'");
        if (countDebit >= 1 && countInvest == 0 && sumSavingDeposit > 1000) {
            System.out.println("Invest true");
            RecomendationDto investRecommendation = new RecomendationDto("Invest 500", UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!");
            return Optional.of(investRecommendation);
        }
        System.out.println("Invest false");
        return Optional.empty();
    }
}
