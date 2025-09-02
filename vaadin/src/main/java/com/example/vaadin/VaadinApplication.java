package com.example.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class
})
public class VaadinApplication {
    public static void main(String[] args) {
        SpringApplication.run(VaadinApplication.class, args);
    }
}

