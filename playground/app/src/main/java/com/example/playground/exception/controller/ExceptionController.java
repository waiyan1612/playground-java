package com.example.playground.exception.controller;

import com.example.playground.base.exception.NotFoundException;
import com.example.playground.base.exception.NotImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/{code}")
    public String get(@PathVariable String code) throws Throwable {
        switch (code) {
            case "403" -> throw new org.springframework.security.access.AccessDeniedException("You shall not pass!");
            case "404.1" -> throw new jakarta.persistence.EntityNotFoundException();
            case "404.2" -> throw new NotFoundException("John Cena");
            default -> throw new NotImplementedException("Coming soon");
        }
    }
}
