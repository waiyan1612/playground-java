package com.example.playground.id.controller;

import com.example.playground.id.model.DocumentResponse;
import com.example.playground.id.model.PersonResponse;
import com.example.playground.id.service.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/id")
public class IdController {

    private final IdService idService;

    @Autowired
    public IdController(IdService idService) {
        this.idService = idService;
    }

    @PostMapping("/search/person")
    public List<PersonResponse> searchByPerson(@RequestParam String name) {
        return idService.searchByPersonName(name);
    }

    @PostMapping("/search/document")
    public List<DocumentResponse> searchByDocumentNumber(@RequestParam String documentNumber) {
        return idService.searchByDocumentNumber(documentNumber);
    }

}
