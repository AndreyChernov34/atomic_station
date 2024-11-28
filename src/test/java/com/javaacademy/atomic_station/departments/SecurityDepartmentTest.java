package com.javaacademy.atomic_station.departments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class SecurityDepartmentTest {
    @Autowired
    private SecurityDepartment securityDepartment;

    @MockBean
    private NuclearStation nuclearStation;

    @Test
    public void addAccidentTest() {
        securityDepartment.addAccident();
        int expected = 1;
        int result = securityDepartment.getAccidentCountPeriod();
        Assertions.assertEquals(expected, result);

        securityDepartment.addAccident();
        securityDepartment.addAccident();
        expected = 3;
        result = securityDepartment.getAccidentCountPeriod();
        Assertions.assertEquals(expected, result);
    }


    /**
     * Передача количества инцидентов в атомную станция и сброс счетчика
     */
    @Test
    public void resetTest() {
        securityDepartment.addAccident();
        securityDepartment.reset();
        int result = securityDepartment.getAccidentCountPeriod();
        int expected = 0;
        Assertions.assertEquals(expected, result);
    }
}
