package com.tuana9a;

import com.tuana9a.config.AppConfig;
import com.tuana9a.service.SearchEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class StackOverFlowSearchEngineApplication implements CommandLineRunner {

    @Autowired
    private AppConfig config;

    @Autowired
    private SearchEngineService searchEngineService;

    public static void main(String[] args) {
        SpringApplication.run(StackOverFlowSearchEngineApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {

    }
}