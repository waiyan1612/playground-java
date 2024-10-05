package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class GreetingServiceMockitoTest {

    @InjectMocks
    GreetingService greetingService;

    @Mock
    NoOpService noOpService;

    @Test
    void testGreet() {
        doNothing().when(noOpService).doNothing();
        assertEquals("Hello", greetingService.greet());
    }
}
