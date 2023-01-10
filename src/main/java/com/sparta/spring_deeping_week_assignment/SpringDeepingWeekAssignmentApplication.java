package com.sparta.spring_deeping_week_assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class SpringDeepingWeekAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDeepingWeekAssignmentApplication.class, args);
    }

}
