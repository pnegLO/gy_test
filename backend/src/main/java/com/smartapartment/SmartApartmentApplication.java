package com.smartapartment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.smartapartment.model")
@EnableJpaRepositories("com.smartapartment.repository")
public class SmartApartmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartApartmentApplication.class, args);
    }
} 