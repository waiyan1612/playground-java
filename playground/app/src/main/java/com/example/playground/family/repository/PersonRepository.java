package com.example.playground.family.repository;

import com.example.playground.family.model.PersonEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    PersonEntity findByName(String name);

    @EntityGraph(attributePaths = {"parents", "children"})
    PersonEntity findByNameIgnoreCase(String name);
}
