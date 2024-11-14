package com.javaacademy.atomic_station.departments;

import lombok.Getter;
import org.springframework.context.annotation.Lazy;

@Lazy
public class SecutiryDepartment {

    private NuclearStation nuclearStation;
    @Getter
    private int accidentCountPeriod;

    public SecutiryDepartment(NuclearStation nuclearStation) {
        this.accidentCountPeriod = 0;
        this.nuclearStation = nuclearStation;
    }

    public void addAccident(){
        accidentCountPeriod += 1;
    }

    public void reset() {
        nuclearStation.incrementAccident(accidentCountPeriod);
        accidentCountPeriod = 0;
    }
}
