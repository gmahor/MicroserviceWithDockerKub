package com.eroom.dtos;

import java.util.List;

public interface DocumentTypeRespDTO {

    Long getId();

    String getDocumentName();

    String getDocumentType();

    String getDocumentAbbreviation();

    String getLevel();

    List<DepartmentMappedWithDocTypeRespDTO> getDocumentTypeMappingWithDepartment();

    List<SupplyVerticalMappedWithDocTypeRespDTO> getDocumentTypeMappingWithSupplyVerticals();

    List<DivisionMappedWithDocTypeRespDTO> getDocumentTypeMappingWithDivision();

    List<OrganisationMappedWithDocTypeRespDTO> getDocumentTypeMappingWithOrganisation();

    List<GeographyMappedWithDocTypeRespDTO> getDocumentTypeMappingWithGeographies();
}
