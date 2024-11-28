package com.javaacademy.atomic_station.economic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Экономический отдел во Франции
 */
@Component
@Profile("france")
@Slf4j
@Getter
public class FranceEconomicDepartment extends EconomicDepartment {
    // базовая (начальная) цена
    @Value("${app.base_price}")
    private BigDecimal basePrice;

    // Лимит энергии, после которого меняется цена
    @Value("${app.upper_limit}")
    private long upperLimit;

    // коэффициент уменьшения цены
    @Value("${app.base_coefficient}")
    private BigDecimal baseCoefficient;

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
            if ((countElectricity - count) > upperLimit) {
                result = price.multiply(BigDecimal.valueOf(upperLimit)).add(result);
                count += upperLimit;
            } else {
                result = price.multiply(BigDecimal.valueOf(countElectricity - count)).add(result);
                count = countElectricity;
            }
            price = price.multiply(baseCoefficient);
        }
         return result.stripTrailingZeros();
    }
}
