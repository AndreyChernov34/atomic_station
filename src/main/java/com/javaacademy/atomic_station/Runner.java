package com.javaacademy.atomic_station;

import com.javaacademy.atomic_station.departments.NuclearStation;
import com.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import com.javaacademy.atomic_station.exception.ReactorWorkException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Runner {
    private static final int YEARS = 3;

    public static void main(String[] args) throws NuclearFuelIsEmptyException, ReactorWorkException {
        ConfigurableApplicationContext context = SpringApplication.run(Runner.class, args);
        NuclearStation nuclearStation = context.getBean(NuclearStation.class);
        nuclearStation.start(YEARS);
    }
}
