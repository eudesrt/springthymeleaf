package com.br.springthymeleaf.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.br.springthymeleaf.webapp.controller , com.br.springthymeleaf.webapp.service", "controller", "service" } )
// If our Controller class or Service class is not in the same packages we have //to add packages's name like this...directory(package) with main class
public class WebappApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}
}
