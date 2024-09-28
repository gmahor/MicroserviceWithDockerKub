package com.eroom.dtos;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.List;

@Data
public class UpdateDocumentTypeDTO {

    @Min(value = 1, message = "Id is required")
    private Long id;

    private List<DivisionDTO> divisionList;

    private List<SupplyVerticalDTO> supplyVerticalList;

    private List<OrganisationDTO> organisationDtoList;

    private List<DepartmentDTO> departmentList;

    private List<GeographyDTO> geographyList;

    private String documentName;

    private String documentType;

    private String documentAbbreviation;

    private String level;

}
