package com.example.config;

import com.example.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public HelloService helloService () {
        return new HelloService();
    }
}
