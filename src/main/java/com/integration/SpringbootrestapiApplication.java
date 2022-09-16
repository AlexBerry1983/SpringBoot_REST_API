package com.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication
public class SpringbootrestapiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootrestapiApplication.class, args);
		
	}
	
	
}
