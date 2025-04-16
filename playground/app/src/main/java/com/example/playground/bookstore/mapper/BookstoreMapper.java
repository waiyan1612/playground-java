package com.example.playground.bookstore.mapper;

import com.example.playground.bookstore.model.AuthorEntity;
import com.example.playground.bookstore.model.AuthorResponse;
import com.example.playground.bookstore.model.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookstoreMapper {

    List<AuthorResponse> authorEntitiesToAuthorResponse(List<AuthorEntity> authorEntities);

    default Set<String> mapBookNames(Set<BookEntity> bookEntities) {
        return bookEntities.stream().map(BookEntity::getName).collect(Collectors.toSet());
    }

    default List<AuthorResponse> bookEntitiesToAuthorResponse(List<BookEntity> bookEntities) {
        HashMap<String, HashSet<String>> authorMap = new HashMap<>();
        for(BookEntity bookEntity: bookEntities) {
            String book = bookEntity.getName();
            String author = bookEntity.getAuthor().getName();
            if (authorMap.containsKey(author)) {
                authorMap.get(author).add(book);
            } else {
                authorMap.put(author, new HashSet<>(Collections.singletonList(book)));
            }
        }
        return authorMap.entrySet().stream().map(entry -> new AuthorResponse(entry.getKey(), entry.getValue())).toList();
    }
}
