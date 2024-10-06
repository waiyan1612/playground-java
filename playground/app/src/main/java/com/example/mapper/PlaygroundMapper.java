package com.example.mapper;

import com.example.dto.HelloDto;
import com.example.entity.HelloEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlaygroundMapper {
    @Mapping(source = "helloEntity.lang", target = "language")
    HelloDto helloEntityToHelloDto(HelloEntity helloEntity);
}
