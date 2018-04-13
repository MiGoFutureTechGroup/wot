package com.migotech.wot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@EnableAutoConfiguration
public class WotApplication {

	public static void main(String[] args) {
		SpringApplication.run(WotApplication.class, args);
	}
}
