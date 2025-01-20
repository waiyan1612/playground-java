package com.example.controller;

import com.example.service.HiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class HiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private HiService hiService;

    private final String basicAuth;

    @Autowired
    public HiControllerTest(@Value("${spring.security.user.name}") String username, @Value("${spring.security.user.password}") String password) {
        basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void testHi() throws Exception {
        given(hiService.greet()).willReturn("hi hi");
        mvc.perform(get("/hi"))
            .andExpect(status().isUnauthorized());
        mvc.perform(get("/hi").header("Authorization", "Bearer whatever"))
                .andExpect(status().isOk())
                .andExpect(content().string("hi hi, admin@jwt"));
        mvc.perform(get("/hi").header("Authorization", basicAuth))
                .andExpect(status().isOk())
                .andExpect(content().string("hi hi, admin@basic"));
    }

    @Test
    void testHiJwt() throws Exception {
        given(hiService.greet()).willReturn("hi hi");
        mvc.perform(get("/hi/jwt").header("Authorization", "Bearer whatever"))
                .andExpect(status().isOk())
                .andExpect(content().string("hi hi, admin@jwt"));
        mvc.perform(get("/hi/jwt").header("Authorization", basicAuth))
                .andExpect(status().isForbidden());
    }

    @Test
    void testHiBasic() throws Exception {
        given(hiService.greet()).willReturn("hi hi");
        mvc.perform(get("/hi/basic").header("Authorization", "Bearer whatever"))
                .andExpect(status().isForbidden());
        mvc.perform(get("/hi").header("Authorization", basicAuth))
                .andExpect(status().isOk())
                .andExpect(content().string("hi hi, admin@basic"));
    }
}
