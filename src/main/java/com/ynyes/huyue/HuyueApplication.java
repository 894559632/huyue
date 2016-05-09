package com.ynyes.huyue;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class HuyueApplication extends SpringBootServletInitializer implements
		CommandLineRunner {

	@Bean
	public CharacterEncodingFilter encodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}
	
	public static void main(String[] args) {

		SpringApplication.run(HuyueApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// Endpoint.publish("/AddService", new BookVO());
	}
}
