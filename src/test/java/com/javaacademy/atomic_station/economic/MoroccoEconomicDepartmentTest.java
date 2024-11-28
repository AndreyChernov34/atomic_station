package com.javaacademy.atomic_station.economic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("morocco")
public class MoroccoEconomicDepartmentTest {

    @Autowired
    MoroccoEconomicDepartment moroccoEconomicDepartment;

    /**
     * Тест при заданном электричестве 3.62 миллиарда кВт·ч
     */
    @Test
    public void computeYearIncomesSuccess() {
        long countElectricity = 3620000000L;
        // результат
        BigDecimal result = moroccoEconomicDepartment.computeYearIncomes(countElectricity);
        // ожидаемое значение
        BigDecimal expectedResult = BigDecimal.valueOf(18100000000L).stripTrailingZeros();
        assertEquals(expectedResult, result);
    }
    /**
     * Тест при выработанном электричестве свыше лимита 5 миллиарда кВт·ч
     */
    @Test
    public void computeYearIncomesUnderLimit() {

        long countElectricity = 6000000000L;
        // результат
        BigDecimal result = moroccoEconomicDepartment.computeYearIncomes(countElectricity);
        // ожидаемое значение
        BigDecimal expectedResult = moroccoEconomicDepartment.getBasePrice().
                multiply(BigDecimal.valueOf(moroccoEconomicDepartment.getUpperLimit())).
                add(BigDecimal.valueOf(countElectricity - moroccoEconomicDepartment.getUpperLimit()).
                        multiply(moroccoEconomicDepartment.getUpperPrice())).
                stripTrailingZeros();
        assertEquals(expectedResult, result);
    }

    /**
     * Тест при выработанном электричестве меньше лимита 5 миллиарда кВт·ч
     */
    @Test
    public void computeYearLessLimit() {

        long countElectricity = 1000000000L;
        // результат
        BigDecimal result = moroccoEconomicDepartment.computeYearIncomes(countElectricity);
        // ожидаемое значение
        BigDecimal expectedResult = moroccoEconomicDepartment.getBasePrice().
                multiply(BigDecimal.valueOf(countElectricity)).stripTrailingZeros();
        assertEquals(expectedResult, result);
    }


}
