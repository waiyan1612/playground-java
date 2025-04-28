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

    @PostMapping("/search/author")
    public List<AuthorBioResponse> searchByAuthor(@RequestParam String name) {
        return bookstoreService.searchByAuthor(name);
    }

    @PostMapping("/search/author-without-bio")
    public List<AuthorResponse> searchByAuthorNoBio(@RequestParam String caseSensitiveName) {
        return bookstoreService.searchByAuthorNoBio(caseSensitiveName);
    }

    @PostMapping("/search/book")
    public List<BookResponse> searchByBookTitle(@RequestParam String title) {
        return bookstoreService.searchByBook(title);
    }

    @PostMapping("/add-book-and-author")
    public void addBookAndAuthor(@RequestBody BookRequest request) {
        bookstoreService.addBookAndAuthor(request);
    }

    @PostMapping("/update-first-book-of-author")
    public void updateFirstBookOfAuthor(@RequestBody BookRequest request) {
        bookstoreService.updateFirstBookOfAuthor(request);
    }
}
