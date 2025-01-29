package com.example.playground.hello.controller;

import com.example.playground.hello.model.HelloResponse;
import com.example.playground.hello.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    private final HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/{lang}")
    public HelloResponse getByLanguage(@PathVariable(value = "lang") String lang) {
        log.info("Received greeting request for {}", lang);
        HelloResponse helloResponse = helloService.greet(lang);
        log.info("Greeting in {} is {}", helloResponse.language(), helloResponse.message());
        return helloResponse;
    }
}
