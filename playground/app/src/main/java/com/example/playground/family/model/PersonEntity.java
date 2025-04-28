package com.example.playground.family.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(schema = "family", name = "person")
public class PersonEntity {

    @Id
    private long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            schema = "family",
            name = "tree",
            joinColumns = { @JoinColumn(name = "child_id") },
            inverseJoinColumns = { @JoinColumn(name = "parent_id") }
    )
    private Set<PersonEntity> parents;

    // Note: Do not use @JoinTable again since hibernate will treat them as separate as double map it.
    // The correct approach is to use `mappedBy`
    @ManyToMany(mappedBy = "parents")
    private Set<PersonEntity> children;

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

    public Set<PersonEntity> getParents() {
        return parents;
    }

    public void setParents(Set<PersonEntity> parents) {
        this.parents = parents;
    }

    public Set<PersonEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<PersonEntity> children) {
        this.children = children;
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
