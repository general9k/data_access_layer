package ru.rodionov.lab_5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.rodionov.lab_5.repository")
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
public class Lab5Application {

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab5Application.class, args);
    }

}
