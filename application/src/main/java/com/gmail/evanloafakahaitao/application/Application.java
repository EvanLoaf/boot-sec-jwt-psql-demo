package com.gmail.evanloafakahaitao.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"com.gmail.evanloafakahaitao"
})
@EnableJpaRepositories(basePackages = {
		"com.gmail.evanloafakahaitao.dao"
})
@EntityScan("com.gmail.evanloafakahaitao.dao.*")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
