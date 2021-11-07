package com.tuana9a;

import com.tuana9a.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StackOverFlowSearchEngineApplication implements CommandLineRunner {

    @Autowired
    private AppConfig config;

    public static void main(String[] args) {
        SpringApplication.run(StackOverFlowSearchEngineApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(config.CUSTOM_NAME);

    }
}