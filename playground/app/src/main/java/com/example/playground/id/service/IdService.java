package com.example.playground.id.service;

import com.example.playground.id.mapper.IdMapper;
import com.example.playground.id.model.DocumentEntity;
import com.example.playground.id.model.DocumentResponse;
import com.example.playground.id.model.PersonEntity;
import com.example.playground.id.model.PersonResponse;
import com.example.playground.id.repository.DocumentRepository;
import com.example.playground.id.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdService {

    @Qualifier("idPersonRepository")
    private final PersonRepository personRepository;
    private final DocumentRepository documentRepository;
    private final IdMapper idMapper;

    @Autowired
    public IdService(PersonRepository personRepository, DocumentRepository documentRepository, IdMapper idMapper) {
        this.personRepository = personRepository;
        this.documentRepository = documentRepository;
        this.idMapper = idMapper;
    }

    public List<PersonResponse> searchByPersonName(String name) {
        List<PersonEntity> personEntities = personRepository.findByNameContainingIgnoreCase(name);
        return idMapper.personEntitiesToPersonResponse(personEntities);
    }

    public List<DocumentResponse> searchByDocumentNumber(String documentNumber) {
        List<DocumentEntity> documents = documentRepository.findByDocumentNumberIgnoreCase(documentNumber);
        return idMapper.documentEntitiesToDocumentResponses(documents);
    }
}
