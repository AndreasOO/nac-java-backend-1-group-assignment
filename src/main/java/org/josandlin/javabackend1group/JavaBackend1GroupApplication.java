package org.josandlin.javabackend1group;

import org.josandlin.javabackend1group.entity.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaBackend1GroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaBackend1GroupApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(/*lägg in repon*/){
        return (args) -> {
            // skapa upp objekt av varje entitet, lägg till allt som behövs i dess constructor
            // se till att tex extratype läggs in först, innan man gör addedextra, eftersom addedextra har fk till extratype
            // kör sen tillhörandeRepo.save(entitetsobjekt)
        };
    }

}
