package com.example.playground.hello.controller;

import com.example.playground.hello.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hi")
public class HiController {

    private final HiService hiService;

    @Autowired
    public HiController(HiService hiService) {
        this.hiService = hiService;
    }

    @GetMapping
    public String get(Authentication authentication) {
        return hiService.greet() + ", " + authentication.getName();
    }

    @GetMapping("/basic")
    @PreAuthorize("authentication.name == 'admin@basic'")
    public String getBasic(Authentication authentication) {
        return get(authentication);
    }

    @GetMapping("/jwt")
    @PreAuthorize("authentication.name == 'admin@jwt'")
    public String getJwt(Authentication authentication) {
        return get(authentication);
    }
}
