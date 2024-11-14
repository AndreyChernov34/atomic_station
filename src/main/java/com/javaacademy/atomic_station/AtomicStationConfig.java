package com.javaacademy.atomic_station;

import com.javaacademy.atomic_station.departments.NuclearStation;
import com.javaacademy.atomic_station.departments.ReactorDepartment;
import com.javaacademy.atomic_station.departments.SecutiryDepartment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AtomicStationConfig {

    @Bean
    ReactorDepartment reactorDepartment(@Lazy SecutiryDepartment secutiryDepartment) {
        return new ReactorDepartment(secutiryDepartment);
    }
    @Bean
    NuclearStation nuclearStation(ReactorDepartment reactorDepartment, @Lazy SecutiryDepartment secutiryDepartment) {
        return new NuclearStation(reactorDepartment, secutiryDepartment);
    }

    @Bean
    @Lazy
    SecutiryDepartment secutiryDepartment(NuclearStation nuclearStation) {
        return new SecutiryDepartment(nuclearStation);
    }

}
