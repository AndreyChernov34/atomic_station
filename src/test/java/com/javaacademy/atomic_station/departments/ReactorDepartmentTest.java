package com.javaacademy.atomic_station.departments;

import com.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import com.javaacademy.atomic_station.exception.ReactorWorkException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

@Profile("france")
@SpringBootTest
public class ReactorDepartmentTest {

    private ReactorDepartment reactorDepartment;
    @MockBean
    private SecurityDepartment securityDepartment;
    @MockBean
    private NuclearStation nuclearStation;

    @BeforeEach
    public void setUp() {
        Mockito.reset();
        reactorDepartment = new ReactorDepartment(securityDepartment);
    }


    /**
     * Остановка реактора
     * @throws ReactorWorkException
     */
    @Test
    public void stopSuccessTest() throws ReactorWorkException, NuclearFuelIsEmptyException {
        reactorDepartment.run();
        reactorDepartment.stop();
        boolean excepted = false;
        boolean result = reactorDepartment.isRun();
        Assertions.assertEquals(excepted, result);
    }

    @Test
    public void stopThrowReactorWorksTest() throws ReactorWorkException {

        ReactorWorkException result = Assertions.assertThrows(ReactorWorkException.class, () -> {
            reactorDepartment.stop();
        });
        Assertions.assertEquals("Реактор уже выключен", result.getMessage());
    }

    @Test
    public void runSuccessTest() throws ReactorWorkException {
        long result = 0;
        try {
            result = reactorDepartment.run();
        } catch (ReactorWorkException | NuclearFuelIsEmptyException e) {
            throw new RuntimeException(e);
        }
        long expected = 10000000L;
        Assertions.assertEquals(expected, result);
        Assertions.assertEquals(true, reactorDepartment.isRun());
    }

    @Test
    public void runThrowReactorWorkTest() throws ReactorWorkException {

        try {
            long result = reactorDepartment.run();
        } catch (ReactorWorkException | NuclearFuelIsEmptyException e) {
            throw new RuntimeException(e);
        }
        ReactorWorkException resultexception = Assertions.assertThrows(ReactorWorkException.class, () -> {
             long result = reactorDepartment.run();
        });
        Assertions.assertEquals("Реактор уже работает", resultexception.getMessage());
    }

    @Test
    public void runThrowNuclearFuelIsEmptyTest() throws ReactorWorkException {
        for (int i = 0; i < 99; i++) {
            try {
                long result = reactorDepartment.run();
                reactorDepartment.stop();
            } catch (ReactorWorkException | NuclearFuelIsEmptyException e) {
                throw new RuntimeException(e);
            }
        }

        NuclearFuelIsEmptyException resultexception = Assertions.assertThrows(NuclearFuelIsEmptyException.class, () -> {
            long result = reactorDepartment.run();
        });
        Assertions.assertEquals("Топливо закончилось", resultexception.getMessage());
    }

}
