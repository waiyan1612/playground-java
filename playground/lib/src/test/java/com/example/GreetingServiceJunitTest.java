package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreetingServiceJunitTest {

    NoOpService noOpService = new NoOpService();
    GreetingService greetingService = new GreetingService(noOpService);

    @Test
    void testGreet() {
        assertEquals("Hello", greetingService.greet());
    }
}
