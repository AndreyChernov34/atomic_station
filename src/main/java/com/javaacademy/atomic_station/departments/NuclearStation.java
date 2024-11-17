package com.javaacademy.atomic_station.departments;

import com.javaacademy.atomic_station.economic.EconomicDepartment;
import com.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import com.javaacademy.atomic_station.exception.ReactorWorkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private SecurityDepartment securityDepartment;

    // Общее количество выработанной энергии
    private long totalEnergyGenerated;

    // количество дней в году
    private static final int DAYS_IN_YEAR = 365;

    // счетчик инцидентов на станции
    private int accidentCountAllTime;

    @Value("${app.country}")
    private String country;
    @Value("${app.currency}")
    private String currency;

    @Autowired
    public NuclearStation(ReactorDepartment reactorDepartment, SecurityDepartment securityDepartment,
                          EconomicDepartment economicDepartment) {
        this.reactorDepartment = reactorDepartment;
        this.securityDepartment = securityDepartment;
        this.economicDepartment = economicDepartment;
        totalEnergyGenerated = 0;
        accidentCountAllTime = 0;
    }

    /**
     * Годовой цикл выработки  электричества
     * @throws NuclearFuelIsEmptyException
     * @throws ReactorWorkException
     */
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
        log.info("Количество инцидентов за год: {}", securityDepartment.getAccidentCountPeriod());
        log.info("Доход за год составил {} {}",
                economicDepartment.computeYearIncomes(totalEnergyGeneratedYear).toPlainString(),
                currency);
        securityDepartment.reset();
    }

    /**
     * Метод запуска атомной станции на заданное количество лет
     * @param year  - количество лет
     * @throws NuclearFuelIsEmptyException
     * @throws ReactorWorkException
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
