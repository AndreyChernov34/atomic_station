package com.javaacademy.atomic_station.economic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Экономический отдел во Франции
 */
@Component
@Profile("france")
public class FranceEconomicDepartment extends EconomicDepartment {
    // базовая (начальная) цена
    @Value("${app.base_price}")
    private BigDecimal basePrice;

    // Лимит энергии, после которого меняется цена
    private static final long BASELIMIT = 1000000000L;

    // коэффициент уменьшения цены
    private static final BigDecimal BASE_COEFFICIENT = BigDecimal.valueOf(0.99);

    /**
     * Метод расчета дохода
     * При каждом новом миллиарде киловатт/часов, стоимость уменьшается на 1%
     * @param countElectricity  -количество выработанного электричества
     * @return   - доход
     */
    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        long count = 0;
        BigDecimal price = basePrice;
        BigDecimal result = BigDecimal.ZERO;
        while (count < countElectricity) {
            if ((countElectricity - count) > BASELIMIT) {
                result = price.multiply(BigDecimal.valueOf(BASELIMIT));
                count += BASELIMIT;
            } else {
                result = price.multiply(BigDecimal.valueOf(countElectricity - count));
                count = countElectricity;
            }
            price = price.multiply(BASE_COEFFICIENT);
        }
         return result.stripTrailingZeros();
    }
}
