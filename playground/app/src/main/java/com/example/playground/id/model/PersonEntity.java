package com.example.playground.id.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

// A qualifier is required because com.example.playground.family.model.PersonEntity will produce a bean with the same name
@Entity(name = "idPerson")
@Table(schema = "id", name = "person")
public class PersonEntity {

    @Id
    private long id;

    private String name;

    // Note: With this mappedBy, we are marking many-to-one as the owning side.
    @OneToMany(mappedBy = "person", cascade = { CascadeType.MERGE })
    private Set<DocumentEntity> documents;

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

    public Set<DocumentEntity> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<DocumentEntity> documents) {
        this.documents = documents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonEntity that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
