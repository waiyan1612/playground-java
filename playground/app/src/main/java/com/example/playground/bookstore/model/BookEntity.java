package com.example.playground.bookstore.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(schema = "bookstore", name = "book")
public class BookEntity {

    // Allocation size need to match the INCREMENT BY defined in the Sequence's DDL
    @SequenceGenerator(name = "book_id_generator", sequenceName = "bookstore.book_id_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
    @Id
    private Long id;

    private String title;

    // JPA recommends to mark many-to-one side as the owning side (of the foreign key)
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "author_id", insertable = true, updatable = false)
    private AuthorEntity author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
