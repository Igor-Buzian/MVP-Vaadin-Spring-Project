package com.example.frontend;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FrontendApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }
}
