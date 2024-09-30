package com.mau.mau_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.mau.mau-project.entity")
@EnableJpaRepositories(basePackages = "com.mau.mau-project.repository")
@ComponentScan(basePackages = "com.mau.mau-project")
public class MauProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MauProjectApplication.class, args);
	}

}
