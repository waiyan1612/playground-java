package com.example.playground.family.controller;

import com.example.playground.family.model.PersonRequest;
import com.example.playground.family.model.PersonResponse;
import com.example.playground.family.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/family")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    public PersonResponse searchByName(@RequestBody PersonRequest request, @RequestParam(required = false) boolean useEntityGraph) {
        return personService.findByName(request, useEntityGraph);
    }
}
