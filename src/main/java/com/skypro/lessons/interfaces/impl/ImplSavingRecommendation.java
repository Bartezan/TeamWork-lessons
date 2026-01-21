package com.skypro.lessons.interfaces.impl;

import com.skypro.lessons.interfaces.RecommendationRuleSet;
import com.skypro.lessons.models.RecomendationDto;
import com.skypro.lessons.repositories.RecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ImplSavingRecommendation implements RecommendationRuleSet {
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

        int sumDebitDeposit = repository.getIntFromCustomRequest("""
                SELECT  SUM(t.AMOUNT)
                FROM TRANSACTIONS AS t
                JOIN USERS u ON t.USER_ID =u.ID
                JOIN PRODUCTS p ON t.PRODUCT_ID =p.ID""" +
                " WHERE t.USER_ID ='" + user + "' AND p.TYPE = 'DEBIT' AND t.TYPE = 'DEPOSIT'");

        int sumSavingDeposit = repository.getIntFromCustomRequest("""
                SELECT  SUM(t.AMOUNT)
                FROM TRANSACTIONS AS t
                JOIN USERS u ON t.USER_ID =u.ID
                JOIN PRODUCTS p ON t.PRODUCT_ID =p.ID
                WHERE t.USER_ID ='""" + user + "' AND p.TYPE = 'SAVING' AND t.TYPE = 'DEPOSIT'");

        int sumDebitWithdraw = repository.getIntFromCustomRequest("""
                SELECT  SUM(t.AMOUNT)
                FROM TRANSACTIONS AS t
                JOIN USERS u ON t.USER_ID =u.ID
                JOIN PRODUCTS p ON t.PRODUCT_ID =p.ID
                WHERE t.USER_ID ='""" + user + "' AND p.TYPE = 'DEBIT' AND t.TYPE = 'WITHDRAW'");

        if (countDebit >= 1 && (sumDebitDeposit >= 50000 || sumSavingDeposit >= 50000) && sumDebitDeposit > sumDebitWithdraw) {
            System.out.println("Saving true");
            RecomendationDto investRecommendation = new RecomendationDto("Top Saving", UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"), """
                    Откройте свою собственную «Копилку» с нашим банком!
                    «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!
                    Преимущества «Копилки»:
                    Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.
                    Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.
                    Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.
                    Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!
                    """);
            return Optional.of(investRecommendation);
        }
        System.out.println("Saving false");
        return Optional.empty();
    }
}
