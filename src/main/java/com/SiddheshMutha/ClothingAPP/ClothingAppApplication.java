package com.SiddheshMutha.ClothingAPP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ClothingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothingAppApplication.class, args);
	}


}
