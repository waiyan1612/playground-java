package com.example.service;

import com.example.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiService {

    private final GreetingService greetingService;

    @Autowired
    public HiService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String greet() {
        char [] charArr = greetingService.greet().toCharArray();
        char [] strikeCharArr = new char[charArr.length * 2];
        for (int i=0; i< charArr.length; i++) {
            strikeCharArr[2*i] = charArr[i];
            strikeCharArr[2*i+1] = '\u0336';
        }
        return String.valueOf(strikeCharArr) + " Hi from Spring";
    }
}
