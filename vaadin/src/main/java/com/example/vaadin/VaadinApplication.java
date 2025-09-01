package com.example.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class VaadinApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaadinApplication.class, args);
	}

}
