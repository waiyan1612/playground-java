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
class HiServiceTest {

    @InjectMocks
    HiService hiService;

    @Mock
    GreetingService greetingService;

    @Test
    void testGreet() {
        Mockito.when(greetingService.greet()).thenReturn("Bye");
        assertEquals("B̶y̶e̶ Hi from Spring", hiService.greet());
    }
}
