package com.javaacademy.atomic_station.departments;

import com.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import com.javaacademy.atomic_station.exception.ReactorWorkException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Реактор
 */
@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class ReactorDepartment {
    // ежедневное количество вырабатываемого электричества
    private static final long ENERGY_LIMIT = 10_000_000;
    // количество запусков между заменой топлива
    private static final int FUELLIMIT = 100;
    // запущен ли реактор
    private boolean isRun = false;
    //счетчик запуска реактора
    private int counter = 0;
    // отдел безопасности
    private final SecurityDepartment securityDepartment;

    /**
     * Запуск реактора
     * @return  возвращает количество выработанного электричества
     * @throws ReactorWorkException
     * @throws NuclearFuelIsEmptyException
     */
    public long run() throws ReactorWorkException, NuclearFuelIsEmptyException {
        if (isRun) {
            securityDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже работает");
        }
        counter++;
        if (counter % FUELLIMIT == 0) {
            securityDepartment.addAccident();
            throw new NuclearFuelIsEmptyException("Топливо закончилось");
        }
        isRun = true;
        return ENERGY_LIMIT;
    }

    /**
     * Остановка реактора
     * @throws ReactorWorkException
     */
    public void stop() throws ReactorWorkException {
        if (!isRun) {
            securityDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже выключен");
        }
        isRun = false;
    }
}
