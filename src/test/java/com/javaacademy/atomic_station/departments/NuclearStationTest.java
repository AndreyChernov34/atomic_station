package com.javaacademy.atomic_station.departments;

import com.javaacademy.atomic_station.economic.EconomicDepartment;
import com.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import com.javaacademy.atomic_station.exception.ReactorWorkException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("france")
@SpringBootTest
@Slf4j
@DirtiesContext
public class NuclearStationTest {

    @MockBean
    private ReactorDepartment reactorDepartment;

    @SpyBean
    private SecurityDepartment securityDepartment;

    @MockBean
    private EconomicDepartment economicDepartment;

  //  @Autowired
    private NuclearStation nuclearStation;

    @Value("${app.country}")
    private String country;

    @Value("${app.currency}")
    private String currency;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        nuclearStation = new NuclearStation(reactorDepartment, securityDepartment, economicDepartment);
    }

    @Test
    public void testStartYearSuccess() throws NuclearFuelIsEmptyException, ReactorWorkException {

        when(reactorDepartment.run()).thenReturn(1L); // Энергия, выработанная за день
        //when(securityDepartment.getAccidentCountPeriod()).thenReturn(2);
        when(economicDepartment.computeYearIncomes(365L)).thenReturn(new BigDecimal("1000"));

        nuclearStation.startYear();
        long expected = 365L;

        assertEquals(expected, nuclearStation.getTotalEnergyGeneratedYear());
    }

    @Test
    public void testIncrementAccident() {
        nuclearStation.incrementAccident(1);
        assertEquals(1, nuclearStation.getAccidentCountAllTime());

        nuclearStation.incrementAccident(2);
        assertEquals(3, nuclearStation.getAccidentCountAllTime());
    }

    @Test
    public void startTest() throws NuclearFuelIsEmptyException, ReactorWorkException {
        when(reactorDepartment.run()).thenReturn(1L);
        //when(securityDepartment.getAccidentCountPeriod()).thenReturn(1);
        when(economicDepartment.computeYearIncomes(365L)).thenReturn(BigDecimal.ONE);

        nuclearStation.start(5);
        long expected = 5;
        assertEquals(0, nuclearStation.getAccidentCountAllTime());

    }
}
