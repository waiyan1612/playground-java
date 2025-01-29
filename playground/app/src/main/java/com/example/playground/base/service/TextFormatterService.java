package com.example.playground.base.service;

import org.springframework.stereotype.Service;

@Service
public class TextFormatterService {
    public String strikeThrough(String text) {
        char [] charArr = text.toCharArray();
        char [] strikeCharArr = new char[charArr.length * 2];
        for (int i=0; i< charArr.length; i++) {
            strikeCharArr[2*i] = charArr[i];
            strikeCharArr[2*i+1] = '\u0336';
        }
        return String.valueOf(strikeCharArr);
    }
}
