package com.example.playground.hello.service;

import com.example.GreetingService;
import com.example.playground.base.service.TextFormatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiService {

    private final GreetingService greetingService;
    private final TextFormatterService textFormatterService;

    @Autowired
    public HiService(GreetingService greetingService, TextFormatterService textFormatterService) {
        this.greetingService = greetingService;
        this.textFormatterService = textFormatterService;
    }

    public String greet() {
        return textFormatterService.strikeThrough(greetingService.greet()) + " Hi from Spring";
    }
}
