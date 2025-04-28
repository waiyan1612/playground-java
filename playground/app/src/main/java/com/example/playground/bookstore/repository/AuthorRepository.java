package com.example.playground.bookstore.repository;

import com.example.playground.bookstore.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    default List<AuthorEntity> findByNameContainingIgnoreCaseWithBio (String name) {
        return findByNameContainingIgnoreCase(name);
    }

    default List<AuthorEntity> findByNameContainingIgnoreCaseNoBio (String name) {
        return findByNameContainingIgnoreCase(name);
    }

    List<AuthorEntity> findByNameContainingIgnoreCase(String name);
}
