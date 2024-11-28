package com.javaacademy.atomic_station.departments;

import com.javaacademy.atomic_station.economic.EconomicDepartment;
import com.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import com.javaacademy.atomic_station.exception.ReactorWorkException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *  Атомная станция
 */
@Slf4j
@Component
public class NuclearStation {
    // реактор
    private ReactorDepartment reactorDepartment;
    // экономический отдел
    private EconomicDepartment economicDepartment;
    //отдел безопасности
    private SecurityDepartment securityDepartment;
    // Количество выработанной энергии в год
    @Getter
    private long totalEnergyGeneratedYear = 0;
    // Общее количество выработанной энергии
    //private long totalEnergyGenerated = 0;

    // количество дней в году
    private static final int DAYS_IN_YEAR = 365;

    // счетчик инцидентов на станции
    @Getter
    private int accidentCountAllTime = 0;

    @Value("${app.country}")
    private String country;
    @Value("${app.currency}")
    private String currency;

    public NuclearStation(@Lazy ReactorDepartment reactorDepartment, @Lazy SecurityDepartment securityDepartment,
                          EconomicDepartment economicDepartment) {
        this.reactorDepartment = reactorDepartment;
        this.securityDepartment = securityDepartment;
        this.economicDepartment = economicDepartment;
    }

    /**
     * Годовой цикл выработки  электричества
     * @throws NuclearFuelIsEmptyException  Кончилось топливо
     * @throws ReactorWorkException         Реактор уже в требуемом режиме работы
     */
    public void startYear() throws NuclearFuelIsEmptyException, ReactorWorkException {
        totalEnergyGeneratedYear = 0;
        log.info("Атомная станция начала работу");
        for (int i = 0; i < DAYS_IN_YEAR; i++) {
            try {
                totalEnergyGeneratedYear += reactorDepartment.run();
                reactorDepartment.stop();
            } catch (ReactorWorkException | NuclearFuelIsEmptyException e) {
                log.info(" Внимание! Происходят работы на атомной станции! Электричества нет!");
            }
        }
        log.info("Атомная станция закончила работу. За год Выработано {} киловатт/часов", totalEnergyGeneratedYear);
        log.info("Количество инцидентов за год: {}", securityDepartment.getAccidentCountPeriod());
        log.info("Доход за год составил {} {}",
                economicDepartment.computeYearIncomes(totalEnergyGeneratedYear).toPlainString(),
                currency);
        securityDepartment.reset();
    }

    /**
     * Метод запуска атомной станции на заданное количество лет
     * @param year  - количество лет
     * @throws NuclearFuelIsEmptyException          Кончилось топливо
     * @throws ReactorWorkException                 Реактор уже в требуемом режиме работы
     */
    public void start(int year) throws NuclearFuelIsEmptyException, ReactorWorkException {
        log.info("Действие происходит в стране: {}", country);
        for (int i = 0; i < year; i++) {
            startYear();
        }
        log.info("Количество инцидентов за всю работу станции: {}", accidentCountAllTime);
    }

    /**
     * Метод накопления количества инцидентов на атомной станции
     */
    public void incrementAccident(int count) {
        accidentCountAllTime += count;
    }
}
