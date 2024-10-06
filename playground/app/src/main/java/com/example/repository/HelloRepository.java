package com.example.repository;

import com.example.entity.HelloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelloRepository extends JpaRepository<HelloEntity, Long> {
    public HelloEntity findMessageByLang(String lang);
}
