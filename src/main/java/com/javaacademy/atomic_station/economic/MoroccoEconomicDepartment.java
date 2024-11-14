package com.javaacademy.atomic_station.economic;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("morocco")
public class MoroccoEconomicDepartment extends EconomicDepartment {
    private BigDecimal BASE_PRICE = BigDecimal.valueOf(0.5);
    private BigDecimal UPPER_PRICE = BigDecimal.valueOf(0.5);
    private long BASE_LIMIT = 5000000000L;
    private BigDecimal BASE_COEFFICIENT = BigDecimal.valueOf(0.99);

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        BigDecimal totalSum = BigDecimal.ZERO;

        if (countElectricity < BASE_LIMIT) {
            totalSum = BASE_PRICE.multiply(BigDecimal.valueOf(countElectricity));
        }
        else {
            totalSum = BASE_PRICE.multiply(BigDecimal.valueOf(BASE_LIMIT));
            totalSum = totalSum.add(UPPER_PRICE.multiply(BigDecimal.valueOf(countElectricity - BASE_LIMIT)));
        }
        return totalSum;
    }
}
