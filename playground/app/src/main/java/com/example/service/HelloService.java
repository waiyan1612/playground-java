package com.example.service;

import com.example.dto.HelloDto;
import com.example.entity.HelloEntity;
import com.example.mapper.PlaygroundMapper;
import com.example.repository.HelloRepository;
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

    public HelloDto greet(String lang) {
        HelloEntity helloEntity = helloRepository.findMessageByLang(lang);
        return playgroundMapper.helloEntityToHelloDto(helloEntity);
    }
}
