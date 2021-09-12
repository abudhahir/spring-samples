package com.cleveloper.sihttp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class SiHttpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiHttpApplication.class, args);
    }

}
