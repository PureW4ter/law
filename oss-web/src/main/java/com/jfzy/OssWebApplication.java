package com.jfzy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jfzy.base.AuthInterceptor;

@SpringBootApplication
@Configuration
public class OssWebApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(OssWebApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new
		// AuthInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new AuthInterceptor());
	}
}
