package com.example.playground.family.model;

import java.util.List;

public record PersonResponse(String name, List<String> parentNames, List<String> childrenNames) {
}
