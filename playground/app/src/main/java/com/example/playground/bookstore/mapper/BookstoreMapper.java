package com.example.playground.bookstore.mapper;

import com.example.playground.bookstore.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookstoreMapper {

    List<AuthorResponse> authorEntitiesToAuthorResponse(List<AuthorEntity> authorEntities);

    List<AuthorBioResponse> authorEntitiesToAuthorWithBioResponse(List<AuthorEntity> authorEntities);

    @Mapping(source = "bio.description", target = "bio")
    AuthorBioResponse authorEntityToAuthorWithBioResponse(AuthorEntity authorEntity);

    default Set<String> mapBookNames(Set<BookEntity> bookEntities) {
        return bookEntities.stream().map(BookEntity::getTitle).collect(Collectors.toSet());
    }

    List<BookResponse> bookEntitiesToBookResponse(List<BookEntity> bookEntities);

    @Mapping(source = "title", target = "bookTitle")
    @Mapping(source = "author.name", target = "authorName")
    BookResponse bookEntityToBookResponse(BookEntity bookEntity);
}
