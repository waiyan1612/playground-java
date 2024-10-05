package com.example.service;

import com.example.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Autowired
    private GreetingService greetingService;

    public String greet() {
        return greetingService.greet() + " from Spring";
    }
}
