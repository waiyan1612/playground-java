package com.example;

public class GreetingService {

    private final NoOpService noOpService;

    public GreetingService(NoOpService noOpService) {
        this.noOpService = noOpService;
    }

    public String greet() {
        noOpService.doNothing();
        return "Hello";
    }
}
