package com.example.mapper;

import com.example.hello.model.HelloResponse;
import com.example.hello.model.HelloEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlaygroundMapper {
    @Mapping(source = "helloEntity.lang", target = "language")
    HelloResponse entityToResponse(HelloEntity helloEntity);
}
