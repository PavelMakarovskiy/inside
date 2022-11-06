package com.inside.insidetask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// данное приложение сделано с использованием Spring Boot
@SpringBootApplication
public class InsideTaskApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(InsideTaskApplication.class, args);
	}

}
