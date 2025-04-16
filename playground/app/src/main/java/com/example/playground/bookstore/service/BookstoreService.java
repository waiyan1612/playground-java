package com.example.playground.bookstore.service;

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

    public List<AuthorBioResponse> searchByAuthor(String name) {
        List<AuthorEntity> authorEntities = authorRepository.findByNameContainingIgnoreCaseWithBio(name);
        return bookstoreMapper.authorEntitiesToAuthorWithBioResponse(authorEntities);
    }

    public List<AuthorResponse> searchByAuthorNoBio(String name) {
        List<AuthorEntity> authorEntities = authorRepository.findByNameContainingIgnoreCaseNoBio(name);
        return bookstoreMapper.authorEntitiesToAuthorResponse(authorEntities);
    }

    public List<BookResponse> searchByBook(String title) {
        List<BookEntity> bookEntities = bookRepository.findByTitleContainingIgnoreCase(title);
        return bookstoreMapper.bookEntitiesToBookResponse(bookEntities);
    }

    /**
     * Requires following configs in {@code BookEntity.author}: <br>
     * 1. {@code cascade = CascadeType.PERSIST}, otherwise it will throw TransientObjectException. <br>
     * 2. {@code @JoinColumn(insertable = true)}, otherwise, it will throw 'ERROR: null value in column "author_id" of relation "book" violates not-null constraint'
     */
    public void addBookAndAuthor(BookRequest request) {
        AuthorEntity author = new AuthorEntity();
        author.setName(request.authorName());
        BookEntity book = new BookEntity();
        book.setAuthor(author);
        book.setTitle(request.bookTitle());
        bookRepository.save(book);
    }

    /**
     * Requires following configs in {@code BookEntity.author}: <br>
     * 1. {@code @JoinColumn(insertable = true)}, otherwise, it will throw 'ERROR: null value in column "author_id" of relation "book" violates not-null constraint'  <br>
     * Requires following configs in {@code AuthorEntity.books}: <br>
     * 1. {@code cascade = CascadeType.MERGE}, otherwise it will ignore the update. <br>
     * If an entity is returned by findFirst, it will be managed by hibernate and the update will be propagated even without CascadeType.MERGE.
     * But if we create a new object, it is not tracked by hibernate. Without CascadeType.MERGE, this row won't be added to the books.
     */
    public void updateFirstBookOfAuthor(BookRequest request) {
        AuthorEntity author = authorRepository.findByNameContainingIgnoreCase(request.authorName()).getFirst();
        BookEntity book = author.getBooks().stream().findFirst().orElseGet(() -> {
            BookEntity newBook = new BookEntity();
            newBook.setAuthor(author);      // updates the owning side
            author.getBooks().add(newBook); // updates the inverse side
            return newBook;
        });
        book.setTitle(request.bookTitle());
        authorRepository.save(author);
    }
}
