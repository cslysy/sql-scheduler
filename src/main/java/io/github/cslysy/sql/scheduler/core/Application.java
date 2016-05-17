package io.github.cslysy.sql.scheduler.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "io.github.cslysy.sql.scheduler")
@EnableScheduling
public class Application {
    
    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }
}