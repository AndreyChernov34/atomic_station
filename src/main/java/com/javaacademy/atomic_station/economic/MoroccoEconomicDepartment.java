package com.javaacademy.atomic_station.economic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Экономический отдел в Морокко
 */
@Component
@Profile("morocco")
public class MoroccoEconomicDepartment extends EconomicDepartment {
    // базовая (начальная) цена
    @Value("${app.base_price}")
    private BigDecimal basePrice;

    //повышенная цена
    private static final BigDecimal UPPER_PRICE = BigDecimal.valueOf(6);

    //лимит электроэнергии для повышения цены
    private static final long BASE_LIMIT = 5000000000L;

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

        if (countElectricity < BASE_LIMIT) {
            result = basePrice.multiply(BigDecimal.valueOf(countElectricity));
        } else {
            result = basePrice.multiply(BigDecimal.valueOf(BASE_LIMIT));
            result = result.add(UPPER_PRICE.multiply(BigDecimal.valueOf(countElectricity - BASE_LIMIT)));
        }
        return result.stripTrailingZeros();
    }
}
