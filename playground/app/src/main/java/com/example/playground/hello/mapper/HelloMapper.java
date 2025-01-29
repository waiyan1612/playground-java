package com.example.playground.hello.mapper;

import com.example.playground.hello.model.HelloResponse;
import com.example.playground.hello.model.HelloEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HelloMapper {
    @Mapping(source = "helloEntity.lang", target = "language")
    HelloResponse entityToResponse(HelloEntity helloEntity);
}
