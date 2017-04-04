package com.jfzy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mangofactory.swagger.plugin.EnableSwagger;

@EnableSwagger
@SpringBootApplication
public class MWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MWebApplication.class, args);
	}
}
