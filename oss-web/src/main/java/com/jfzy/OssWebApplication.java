package com.jfzy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jfzy.base.AuthInterceptor;
import com.jfzy.base.ExceptionFilter;

@SpringBootApplication
@Configuration
public class OssWebApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(OssWebApplication.class, args);
	}

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor());
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		ExceptionFilter exceptionFilter = new ExceptionFilter();
		registrationBean.setFilter(exceptionFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/api/*");
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}
}
