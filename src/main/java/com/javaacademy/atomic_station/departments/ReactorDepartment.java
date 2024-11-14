package com.javaacademy.atomic_station.departments;

import com.javaacademy.atomic_station.exception.NuclearFuelIsEmptyException;
import com.javaacademy.atomic_station.exception.ReactorWorkException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Getter
public class ReactorDepartment {
    private boolean isRun = false;
    private int counter = 0;
    private static final long ENERGY_LIMIT =10_000_000;
    private SecutiryDepartment secutiryDepartment;

    @Autowired
    public ReactorDepartment(SecutiryDepartment secutiryDepartment) {
        this.secutiryDepartment = secutiryDepartment;
    }

    public long run() throws ReactorWorkException, NuclearFuelIsEmptyException{

        counter++;
        if (counter % 100 == 0) {
            secutiryDepartment.addAccident();
            throw new NuclearFuelIsEmptyException("Топливо закончилось");
        }

        if (isRun) {
            secutiryDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже работает");
        }
        isRun = true;

        return ENERGY_LIMIT;
    }

    public void stop() throws ReactorWorkException{
        if (!isRun) {
            secutiryDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже выключен");
        }
        isRun = false;
    }


}
