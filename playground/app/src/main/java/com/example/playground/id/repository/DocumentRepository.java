package com.example.playground.id.repository;

import com.example.playground.id.model.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    List<DocumentEntity> findByDocumentNumberIgnoreCase(String documentNumber);
}
