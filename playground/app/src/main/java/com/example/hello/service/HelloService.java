package com.example.hello.service;

import com.example.hello.model.HelloEntity;
import com.example.hello.model.HelloResponse;
import com.example.hello.repository.HelloRepository;
import com.example.mapper.PlaygroundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private final HelloRepository helloRepository;
    private final PlaygroundMapper playgroundMapper;

    @Autowired
    public HelloService(HelloRepository helloRepository, PlaygroundMapper playgroundMapper) {
        this.helloRepository = helloRepository;
        this.playgroundMapper = playgroundMapper;
    }

    public HelloResponse greet(String lang) {
        HelloEntity helloEntity = helloRepository.findMessageByLang(lang);
        return playgroundMapper.entityToResponse(helloEntity);
    }
}
