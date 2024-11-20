package com.javaacademy.atomic_station.departments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Отдел безопасности
 */
@Component
@RequiredArgsConstructor
public class SecurityDepartment {

    private final NuclearStation nuclearStation;

    //количество инцидентов за период
    @Getter
    private int accidentCountPeriod;

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
