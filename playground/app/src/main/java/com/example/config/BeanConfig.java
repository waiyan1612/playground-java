package com.example.config;

import com.example.GreetingService;
import com.example.NoOpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public NoOpService noOpService() {
        return new NoOpService();
    }

    @Bean
    public GreetingService greetingService() {
        return new GreetingService(noOpService());
    }

}
