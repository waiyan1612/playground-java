package com.example.playground.bookstore.model;

import java.util.Set;

public record AuthorBioResponse(String name, String bio, Set<String> books) {
}
