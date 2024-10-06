package com.example.service;

import com.example.GreetingService;
import com.example.dto.HelloDto;
import com.example.entity.HelloEntity;
import com.example.mapper.PlaygroundMapper;
import com.example.repository.HelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private final GreetingService greetingService;
    private final HelloRepository helloRepository;
    private final PlaygroundMapper playgroundMapper;

    @Autowired
    public HelloService(GreetingService greetingService, HelloRepository helloRepository, PlaygroundMapper playgroundMapper) {
        this.greetingService = greetingService;
        this.helloRepository = helloRepository;
        this.playgroundMapper = playgroundMapper;
    }

    public String greet() {
        return greetingService.greet() + " from Spring";
    }

    public HelloDto greetByLanguage(String lang) {
        HelloEntity helloEntity = helloRepository.findMessageByLang(lang);
        return playgroundMapper.helloEntityToHelloDto(helloEntity);
    }
}
