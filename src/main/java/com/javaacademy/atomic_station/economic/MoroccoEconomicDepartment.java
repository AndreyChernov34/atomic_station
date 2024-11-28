package com.javaacademy.atomic_station.economic;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Экономический отдел в Морокко
 */
@Component
@Profile("morocco")
@Getter
public class MoroccoEconomicDepartment extends EconomicDepartment {
    // базовая (начальная) цена
    @Value("${app.base_price}")
    private BigDecimal basePrice;

    //повышенная цена
    @Value("${app.upper_price}")
    private BigDecimal upperPrice;

    //лимит электроэнергии для повышения цены
    @Value("${app.upper_limit}")
    private long upperLimit;

    /**
     * метод расчета дохода от произведенной энергии
     * базовый доход 1 киловатт/часа - 5 дирхам. Если было произведено больше 5 миллиардов киловатт/часов,
     * т.е все остальные считаются по увеличенному доходу 6 дирхам
     * @param countElectricity - количество произведенной энергии
     * @return - возвращает доход
     */
    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        BigDecimal result = BigDecimal.ZERO;

        if (countElectricity < upperLimit) {
            result = basePrice.multiply(BigDecimal.valueOf(countElectricity));
        } else {
            result = basePrice.multiply(BigDecimal.valueOf(upperLimit));
            result = result.add(upperPrice.multiply(BigDecimal.valueOf(countElectricity - upperLimit)));
        }
        return result.stripTrailingZeros();
    }
}
