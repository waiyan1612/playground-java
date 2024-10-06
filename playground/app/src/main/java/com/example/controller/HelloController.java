package com.example.controller;

import com.example.dto.HelloDto;
import com.example.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private final HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping
    public String get() {
        return helloService.greet();
    }

    @GetMapping("/{lang}")
    public HelloDto getByLanguage(@PathVariable(value = "lang") String lang) {
        return helloService.greetByLanguage(lang);
    }
}
