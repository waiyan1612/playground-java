package com.example.playground.bookstore.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(schema = "bookstore", name = "book")
public class BookEntity {

    @Id
    private long id;

    private String name;

    // JPA recommends to mark many-to-one side as the owning side
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "author_id", insertable = true, updatable = true)
    private AuthorEntity author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
