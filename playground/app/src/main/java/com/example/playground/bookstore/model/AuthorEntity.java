package com.example.playground.bookstore.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(schema = "bookstore", name = "author")
public class AuthorEntity {

    @Id
    private long id;

    private String name;

    // Note: With this mappedBy, we are marking many-to-one as the owning side.
    // If we want to mark one-to-many as the owning side, remove this mappedBy and
    // add  @JoinColumn(name = "author_id", insertable = false, updatable = false)
    // but this is not preferred or recommended.
    @OneToMany(mappedBy = "author")
    private Set<BookEntity> books;

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

    public Set<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(Set<BookEntity> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorEntity that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
