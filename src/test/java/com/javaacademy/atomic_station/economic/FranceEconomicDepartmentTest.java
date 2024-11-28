package com.javaacademy.atomic_station.economic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тест FranceEconomicDepartment
 */
@SpringBootTest
@ActiveProfiles("france")
class FranceEconomicDepartmentTest {

    @Autowired
    private FranceEconomicDepartment franceEconomicDepartment;

    /**
     * Тест по заданным параметрам 3.62 миллиарда кВт·ч
     */
    @Test
    void computeYearIncomesSuccess() {

        long countElectricity = 3620000000L;
        // результат
        BigDecimal result = franceEconomicDepartment.computeYearIncomes(countElectricity);
        // ожидаемое значение
        BigDecimal expectedResult = BigDecimal.valueOf(1785842690).stripTrailingZeros();
        assertEquals(expectedResult, result);
    }

    /**
     * Тест при выработанном электричестве меньше лимита в 1 миллиард кВт·ч
     */
    @Test
    void computeYearIncomesLessLimit() {
        long countElectricity = 500_000_000L;
        // результат
        BigDecimal result = franceEconomicDepartment.computeYearIncomes(countElectricity);
        // ожидаемое значение
        BigDecimal expectedResult = BigDecimal.valueOf(countElectricity).
                multiply(franceEconomicDepartment.getBasePrice()).stripTrailingZeros();

        assertEquals(expectedResult, result);
    }

    /**
     * Тест при выработанном электричестве больше лимита в 1 миллиард кВт·ч
     */
    @Test
    void computeYearIncomesUnderLimit() {
        long countElectricity = 2_500_000_000L;
        // результат
        BigDecimal result = franceEconomicDepartment.computeYearIncomes(countElectricity);
        // ожидаемое значение
        BigDecimal expectedResult = BigDecimal.valueOf(franceEconomicDepartment.getUpperLimit()).
                multiply(franceEconomicDepartment.getBasePrice())
                .add(BigDecimal.valueOf(franceEconomicDepartment.getUpperLimit()).
                        multiply(franceEconomicDepartment.getBasePrice().
                        multiply(franceEconomicDepartment.getBaseCoefficient())))
                .add(BigDecimal.valueOf(countElectricity - franceEconomicDepartment.getUpperLimit()
                        - franceEconomicDepartment.getUpperLimit()).multiply(franceEconomicDepartment.getBasePrice().
                        multiply(franceEconomicDepartment.getBaseCoefficient()).multiply(franceEconomicDepartment.
                                getBaseCoefficient()))).stripTrailingZeros();
        assertEquals(expectedResult, result);
    }
}

