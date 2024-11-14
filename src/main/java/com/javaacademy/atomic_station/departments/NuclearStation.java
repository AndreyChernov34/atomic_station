package com.javaacademy.atomic_station.departments;

import com.javaacademy.atomic_station.economic.EconomicDepartment;
import com.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import com.javaacademy.atomic_station.exception.ReactorWorkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class NuclearStation {
    private ReactorDepartment reactorDepartment;
    private SecutiryDepartment secutiryDepartment;
    //private EconomicDepartment economicDepartment;
    private long totalEnergyGenerated;
    private final static int DAYS_IN_YEAR =365;
    private int accidentCountAllTime;

    @Autowired
    public NuclearStation(ReactorDepartment reactorDepartment, SecutiryDepartment secutiryDepartment) {
        this.reactorDepartment = reactorDepartment;
        this.secutiryDepartment = secutiryDepartment;
        totalEnergyGenerated = 0;
        accidentCountAllTime = 0;
    }

    private void startYear() throws NuclearFuelIsEmptyException, ReactorWorkException {
        long totalEnergyGeneratedYear = 0;
      log.info("Атомная станция начала работу");
        for (int i = 0; i < DAYS_IN_YEAR; i++) {
            try {
                log.info("Атомная станция начала работу");

                totalEnergyGeneratedYear += reactorDepartment.run();

                log.info("Атомная станция закончила работу");
                reactorDepartment.stop();

            } catch (ReactorWorkException | NuclearFuelIsEmptyException e) {
                log.info(" Внимание! Происходят работы на атомной станции! Электричества нет!");
            }
        }
        log.info("Атомная станция закончила работу. За год Выработано {} киловатт/часов", totalEnergyGeneratedYear);
        log.info("Количество инцидентов за год: {}", secutiryDepartment.getAccidentCountPeriod());
        secutiryDepartment.reset();

    }

    public void start(int year) throws NuclearFuelIsEmptyException, ReactorWorkException{
        for (int i = 0; i < year ; i++) {
            startYear();
        }
        log.info("Количество инцидентов за всю работу станции: {}", accidentCountAllTime);
    }

    public void incrementAccident(int count) {
        accidentCountAllTime += count;
    }
}
