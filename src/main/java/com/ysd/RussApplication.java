package com.ysd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.ysd.repository")
public class RussApplication {

	public static void main(String[] args) {
		SpringApplication.run(RussApplication.class, args);
	}

}
