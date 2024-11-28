package com.javaacademy.atomic_station.departments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Отдел безопасности
 */
@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class SecurityDepartment {
    private final NuclearStation nuclearStation;

    //количество инцидентов за период
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
