package com.example.playground.family.mapper;

import com.example.playground.family.model.PersonEntity;
import com.example.playground.family.model.PersonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {
    @Mapping(source = "personEntity.children", target = "childrenNames")
    @Mapping(source = "personEntity.parents", target = "parentNames")
    PersonResponse entityToResponse(PersonEntity personEntity);

    default String mapPersonEntityToString(PersonEntity personEntity) {
        return personEntity.getName();
    }
}
