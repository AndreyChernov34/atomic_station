package com.javaacademy.atomic_station.departments;

import com.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import com.javaacademy.atomic_station.exception.ReactorWorkException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("france")
@SpringBootTest
@Slf4j
public class NuclearStationIntegrationTest {
    // количество дней в году
    private static final int DAYS_IN_YEAR = 365;
    // количество запусков реактора для дозаправки толпливом
    private static final int FUEL_LIMIT = 100;
    // количество лет
    private static final int YEAR = 5;
    // ежедневное количество вырабатываемого электричества
    private static final long ENERGY_LIMIT = 10_000_000L;
    // счетчик инцидентов на станции
    private int accidentCountAllTime = 0;
    @Autowired
    private NuclearStation nuclearStation;


    @Test
    public void startYearTest() throws NuclearFuelIsEmptyException, ReactorWorkException {
        long expectedtotalEnergyGenerated = ENERGY_LIMIT * (DAYS_IN_YEAR - 3);
        nuclearStation.startYear();
        Assertions.assertEquals(expectedtotalEnergyGenerated, nuclearStation.getTotalEnergyGeneratedYear());
    }

    @Test
    public void startTest() throws NuclearFuelIsEmptyException, ReactorWorkException {
        long expectedaccidentCountAllTime = (int) (DAYS_IN_YEAR * YEAR) / FUEL_LIMIT;
        nuclearStation.start(YEAR);
        Assertions.assertEquals(expectedaccidentCountAllTime, nuclearStation.getAccidentCountAllTime());
    }

}
