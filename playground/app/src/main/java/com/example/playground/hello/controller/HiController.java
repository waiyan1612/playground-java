package com.example.playground.hello.controller;

import com.example.playground.hello.service.HiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hi")
public class HiController {

    private static final Logger log = LoggerFactory.getLogger(HiController.class);

    private final HiService hiService;

    @Autowired
    public HiController(HiService hiService) {
        this.hiService = hiService;
    }

    @GetMapping
    public String get(Authentication authentication) {
        log.info("Received greeting request for {}", authentication.getName());
        return hiService.greet() + ", " + authentication.getName();
    }

    @GetMapping("/basic")
    @PreAuthorize("authentication.name == 'admin@basic'")
    public String getBasic(Authentication authentication) {
        log.info("Received basic auth greeting request for {}", authentication.getName());
        return get(authentication);
    }

    @GetMapping("/jwt")
    @PreAuthorize("authentication.name == 'admin@jwt'")
    public String getJwt(Authentication authentication) {
        log.info("Received JWT auth greeting request for {}", authentication.getName());
        return get(authentication);
    }
}
