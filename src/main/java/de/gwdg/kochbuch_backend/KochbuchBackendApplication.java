package de.gwdg.kochbuch_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "de.gwdg.kochbuch_backend.model.dto") // Hier das Package mit deinen Entity-Klassen
@EnableJpaRepositories(basePackages = "de.gwdg.kochbuch_backend.model.dao") // Repository-Package
public class KochbuchBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KochbuchBackendApplication.class, args);
    }

}
