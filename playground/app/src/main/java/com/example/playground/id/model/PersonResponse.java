package com.example.playground.id.model;

import java.util.Set;

public record PersonResponse (String name, Set<String> documents) {
}
