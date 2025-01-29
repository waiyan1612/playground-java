package com.example.playground.hello.service;

import com.example.playground.base.exception.NotFoundException;
import com.example.playground.hello.model.HelloEntity;
import com.example.playground.hello.model.HelloResponse;
import com.example.playground.hello.repository.HelloRepository;
import com.example.playground.hello.mapper.HelloMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private final HelloRepository helloRepository;
    private final HelloMapper helloMapper;

    @Autowired
    public HelloService(HelloRepository helloRepository, HelloMapper helloMapper) {
        this.helloRepository = helloRepository;
        this.helloMapper = helloMapper;
    }

    public HelloResponse greet(String lang) {
        HelloEntity helloEntity = helloRepository.findMessageByLang(lang);
        if (helloEntity == null) {
            throw new NotFoundException("No greetings found for " + lang);
        }
        return helloMapper.entityToResponse(helloEntity);
    }
}
