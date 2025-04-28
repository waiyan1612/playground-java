package com.example.playground.bookstore.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(schema = "bookstore", name = "author_bio")
public class AuthorBioEntity {

    @Id
    // No @GeneratedValue because we want this to follow AuthorEntity
    private long id;

    private String description;

    @OneToOne
    // PrimaryKeyJoinColumn is used instead of JoinColumn since we are using the same pk for both classes
    @PrimaryKeyJoinColumn
    private AuthorEntity author;

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorBioEntity that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
