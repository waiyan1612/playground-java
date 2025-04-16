package com.example.playground.bookstore.service;

import com.example.playground.base.exception.NotImplementedException;
import com.example.playground.bookstore.mapper.BookstoreMapper;
import com.example.playground.bookstore.model.*;
import com.example.playground.bookstore.repository.AuthorRepository;
import com.example.playground.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookstoreService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookstoreMapper bookstoreMapper;

    @Autowired
    public BookstoreService(AuthorRepository authorRepository, BookRepository bookRepository, BookstoreMapper bookstoreMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.bookstoreMapper = bookstoreMapper;
    }

    public List<AuthorResponse> search(SearchRequest req) {
        if (req.authorName() != null && req.bookName() != null) {
           throw new NotImplementedException("Provide either author name or book name, not both.");
        } else if (req.authorName() != null) {
            List<AuthorEntity> authorEntities = authorRepository.findByNameContainingIgnoreCase(req.authorName());
            return bookstoreMapper.authorEntitiesToAuthorResponse(authorEntities);
        } else if (req.bookName() != null) {
            List<BookEntity> bookEntities = bookRepository.findByNameContainingIgnoreCase(req.bookName());
            return bookstoreMapper.bookEntitiesToAuthorResponse(bookEntities);
        } else {
            throw new NotImplementedException("Provide either author name or book name");
        }
    }
}
