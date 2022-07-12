package com.senerk.casestudy.flightplanning;

import com.senerk.casestudy.flightplanning.service.project.DbPatcherBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlightplanningApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightplanningApplication.class, args);
    }

    // Some data for first init of DB
    @Bean("initializationBean")
    public CommandLineRunner init(DbPatcherBean dbPatcherBean) {
        return arg -> dbPatcherBean.dbInitializer();
    }
}
