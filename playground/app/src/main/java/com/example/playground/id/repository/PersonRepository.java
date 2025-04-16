package com.example.playground.id.repository;

import com.example.playground.id.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// A qualifier is required because com.example.playground.family.repository.PersonRepository will produce a bean with the same name
@Repository("idPersonRepository")
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    List<PersonEntity> findByNameContainingIgnoreCase(String name);
}
