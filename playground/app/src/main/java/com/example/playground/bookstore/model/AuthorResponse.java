package com.example.playground.bookstore.model;

import java.util.Set;

public record AuthorResponse(String name, Set<String> books) {
}
