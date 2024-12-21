package com.example.service;

import com.example.GreetingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.Text;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HiServiceTest {

    @InjectMocks
    HiService hiService;

    @Mock
    GreetingService greetingService;

    @Mock
    TextFormatterService textFormatterService;

    @Test
    void testGreet() {
        Mockito.when(greetingService.greet()).thenReturn("Bye");
        Mockito.when(textFormatterService.strikeThrough("Bye")).thenReturn("B̶y̶e̶");
        assertEquals("B̶y̶e̶ Hi from Spring", hiService.greet());
    }
}
