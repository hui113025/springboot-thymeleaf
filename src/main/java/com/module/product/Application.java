package com.module.product;

import com.module.core.annotation.EnableMybatis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMybatis
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
