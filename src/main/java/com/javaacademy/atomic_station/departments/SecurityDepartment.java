package com.javaacademy.atomic_station.departments;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Отдел безопасности
 */
@Component
public class SecurityDepartment {

    private NuclearStation nuclearStation;

    //количество инцидентов за период
    @Getter
    private int accidentCountPeriod;

    @Autowired
    public SecurityDepartment(@Lazy NuclearStation nuclearStation) {
        this.accidentCountPeriod = 0;
        this.nuclearStation = nuclearStation;
    }

    public void addAccident() {
        accidentCountPeriod += 1;
    }

    /**
     * Передача количества инцидентов в атомную станция и сброс счетчика
     */
    public void reset() {
        nuclearStation.incrementAccident(accidentCountPeriod);
        accidentCountPeriod = 0;
    }
}
