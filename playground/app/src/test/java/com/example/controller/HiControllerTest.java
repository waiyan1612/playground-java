package com.example.controller;

import com.example.service.HiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private HiService hiService;

    @Test
    void testGetHiController() throws Exception {

        given(hiService.greet()).willReturn("hi hi");
        mvc.perform(get("/hi"))
                .andExpect(status().isOk())
                .andExpect(content().string("hi hi"))
        ;
    }
}
