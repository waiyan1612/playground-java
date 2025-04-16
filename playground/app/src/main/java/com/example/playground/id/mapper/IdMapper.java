package com.example.playground.id.mapper;

import com.example.playground.id.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IdMapper {

    List<PersonResponse> personEntitiesToPersonResponse(List<PersonEntity> personEntities);

    default Set<String> mapDocumentNumbers(Set<DocumentEntity> documentEntities) {
        return documentEntities.stream().map(DocumentEntity::getDocumentNumber).collect(Collectors.toSet());
    }

    // Subclass mappings for document, passport and driving license
    @SubclassMapping(source = PassportEntity.class, target = PassportResponse.class)
    @SubclassMapping(source = DrivingLicenseEntity.class, target = DrivingLicenseResponse.class)
    @Mapping(source = "person.name", target = "heldBy")
    DocumentResponse documentEntityToDocumentResponse(DocumentEntity documentEntity);

    List<DocumentResponse> documentEntitiesToDocumentResponses(List<DocumentEntity> documentEntities);
}
