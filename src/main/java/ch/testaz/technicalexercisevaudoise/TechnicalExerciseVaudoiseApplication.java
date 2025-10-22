package ch.testaz.technicalexercisevaudoise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TechnicalExerciseVaudoiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnicalExerciseVaudoiseApplication.class, args);
    }

}
