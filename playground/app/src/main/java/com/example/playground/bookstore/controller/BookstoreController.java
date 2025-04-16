package com.example.playground.bookstore.controller;

import com.example.playground.bookstore.model.*;
import com.example.playground.bookstore.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookstore")
public class BookstoreController {

    private final BookstoreService bookstoreService;

    @Autowired
    public BookstoreController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @PostMapping("/search")
    public List<AuthorResponse> searchByAuthorName(@RequestBody SearchRequest request) {
        return bookstoreService.search(request);
    }
}
