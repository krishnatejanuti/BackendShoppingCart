//package com.usermicroservice.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer 
//{
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) 
//    {
//        registry.addMapping("/**") // Apply to all end points
//                .allowedOrigins("http://localhost:4200") // Allow requests from this origin
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow HTTP methods
//                .allowedHeaders("*") // Allow all headers
//                .allowCredentials(true)
//                .maxAge(3600); // Allow credentials (cookies, etc.)
//    }
//}