package com.example.playground.family.service;

import com.example.playground.family.mapper.PersonMapper;
import com.example.playground.family.model.PersonEntity;
import com.example.playground.family.model.PersonRequest;
import com.example.playground.family.model.PersonResponse;
import com.example.playground.family.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Autowired
    public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public PersonResponse findByName(PersonRequest req, boolean useEntityGraph) {
        PersonEntity personEntity = useEntityGraph ? personRepository.findByNameIgnoreCase(req.name()) :
                personRepository.findByName(req.name());
            return personMapper.entityToResponse(personEntity);
    }
}
