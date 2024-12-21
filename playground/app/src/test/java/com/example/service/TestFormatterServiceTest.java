package com.example.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestFormatterServiceTest {

    TextFormatterService textFormatterService = new TextFormatterService();

    @Test
    void testStrikeThrough() {
        String expected = "m̶a̶r̶k̶e̶t̶i̶n̶g̶ ̶v̶i̶c̶t̶i̶m̶";
        String actual = textFormatterService.strikeThrough("marketing victim");
        assertEquals(expected, actual);
    }
}
