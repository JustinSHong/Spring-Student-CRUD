package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student justin = new Student("Justin", "jhong@zipwhip.com", LocalDate.of(2000, Month.DECEMBER, 1));
            Student alex = new Student("Alex", "alex@zipwhip.com", LocalDate.of(2001, Month.JANUARY, 20));

            studentRepository.saveAll(List.of(justin, alex));
        };
    }
}
