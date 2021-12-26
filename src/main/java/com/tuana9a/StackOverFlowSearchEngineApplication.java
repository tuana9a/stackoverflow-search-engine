package com.tuana9a;

import com.tuana9a.config.AppConfig;
import com.tuana9a.service.SearchEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;


@SpringBootApplication
@EnableSwagger2
public class StackOverFlowSearchEngineApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StackOverFlowSearchEngineApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {

    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }
}