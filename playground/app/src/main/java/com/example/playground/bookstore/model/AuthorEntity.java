package com.example.playground.bookstore.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(schema = "bookstore", name = "author")
public class AuthorEntity {

    @Id
    // The downside of IDENTITY generation is it disables batch inserts
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    // Note: With this mappedBy, we are marking many-to-one as the owning side.
    // If we want to mark one-to-many as the owning side, remove this mappedBy and
    // add  @JoinColumn(name = "author_id", insertable = false, updatable = false)
    // but this is not preferred or recommended.
    @OneToMany(mappedBy = "author", cascade = { CascadeType.MERGE })
    private Set<BookEntity> books;

    // OneToOne mapping may not respect Lazy Fetch.
    // optional=false will force it to be lazy but if the other size does not have the entry, it will raise an exception.
    // See https://stackoverflow.com/a/36143746 and https://discourse.hibernate.org/t/hibernate-5-2-18-cannot-get-a-lazy-onetoone-anymore/4881/5
    @OneToOne(mappedBy = "author", fetch = FetchType.LAZY, optional = true,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private AuthorBioEntity bio;

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

    public AuthorBioEntity getBio() {
        return bio;
    }

    public void setBio(AuthorBioEntity bio) {
        this.bio = bio;
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
