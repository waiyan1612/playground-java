package com.example.playground.bookstore.model;

import java.util.Set;

public record AuthorRequest(String name, Set<String> books) {
}
