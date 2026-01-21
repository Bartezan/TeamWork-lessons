package com.skypro.lessons.interfaces.impl;

import com.skypro.lessons.interfaces.RecommendationRuleSet;
import com.skypro.lessons.models.RecomendationDto;
import com.skypro.lessons.repositories.RecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ImplCreditRecommendation implements RecommendationRuleSet {
    @Autowired
    private RecommendationsRepository repository;

    @Override
    public Optional<RecomendationDto> getRecommendation(UUID user) {
        int countCredit = repository.getIntFromCustomRequest("""
                SELECT COUNT(*)
                FROM TRANSACTIONS AS t
                JOIN USERS u ON t.USER_ID =u.ID
                JOIN PRODUCTS p ON t.PRODUCT_ID =p.ID""" +
                " WHERE t.USER_ID ='" + user + "' AND p.TYPE = 'CREDIT'");

        int sumDebitDeposit = repository.getIntFromCustomRequest("""
                SELECT  SUM(t.AMOUNT)
                FROM TRANSACTIONS AS t
                JOIN USERS u ON t.USER_ID =u.ID
                JOIN PRODUCTS p ON t.PRODUCT_ID =p.ID""" +
                " WHERE t.USER_ID ='" + user + "' AND p.TYPE = 'DEBIT' AND t.TYPE = 'DEPOSIT'");


        int sumDebitWithdraw = repository.getIntFromCustomRequest("""
                SELECT  SUM(t.AMOUNT)
                FROM TRANSACTIONS AS t
                JOIN USERS u ON t.USER_ID =u.ID
                JOIN PRODUCTS p ON t.PRODUCT_ID =p.ID
                WHERE t.USER_ID ='""" + user + "' AND p.TYPE = 'DEBIT' AND t.TYPE = 'WITHDRAW'");
        if (countCredit == 0 && sumDebitDeposit > sumDebitWithdraw && sumDebitWithdraw > 100000) {
            System.out.println("Credit true");
            RecomendationDto investRecommendation = new RecomendationDto("Простой кредит", UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), """
                    Откройте мир выгодных кредитов с нами!               
                    Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.                    
                    Почему выбирают нас:                    
                    Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.                    
                    Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.                    
                    Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.                    
                    Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!
                    """);
            return Optional.of(investRecommendation);
        }
        System.out.println("Credit false");
        return Optional.empty();
    }
}
