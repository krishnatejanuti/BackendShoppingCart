package com.ordermicroservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderServiceApplication 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
    ModelMapper modelMapper() 
	{
        return new ModelMapper();
    }
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Enable CORS for all endpoints (all paths)
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
						.allowedHeaders("*") // Allow all headers
						.allowCredentials(true); // Allow sending cookies or credentials with requests
			}
		};
	}
	
}
