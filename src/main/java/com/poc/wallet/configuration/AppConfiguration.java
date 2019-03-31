package com.poc.wallet.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.poc.wallet.filter.JWTFilter;


@Configuration
public class AppConfiguration extends WebMvcConfigurationSupport {

	@Bean
	public JWTFilter getjwtInterceptor() {
	    return new JWTFilter();
	}
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(getjwtInterceptor()).addPathPatterns("/user/**");
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
