package com.example.service;

import com.example.GreetingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HelloServiceTest {

    @InjectMocks
    HelloService helloService;

    @Mock
    GreetingService greetingService;

    @Test
    void testGreet() {
        Mockito.when(greetingService.greet()).thenReturn("Hello");
        assertEquals("Hello from Spring", helloService.greet());
    }
}