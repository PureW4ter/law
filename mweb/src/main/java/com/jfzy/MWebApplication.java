package com.jfzy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jfzy.mweb.base.AuthInterceptor;

@SpringBootApplication
public class MWebApplication extends WebMvcConfigurerAdapter {

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor());
	}

	public static void main(String[] args) {
		SpringApplication.run(MWebApplication.class, args);
	}
}
