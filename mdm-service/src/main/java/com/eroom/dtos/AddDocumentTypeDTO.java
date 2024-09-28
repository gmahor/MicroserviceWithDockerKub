package com.eroom.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AddDocumentTypeDTO {

    @NotEmpty(message = "Division List Is Required.")
    private List<DivisionDTO> divisionList;

    @NotEmpty(message = "Supply Vertical List Is Required.")
    private List<SupplyVerticalDTO> supplyVerticalList;

    @NotEmpty(message = "Supply Vertical List Is Required.")
    private List<OrganisationDTO> organisationDtoList;

    @NotEmpty(message = "Supply Vertical List Is Required.")
    private List<DepartmentDTO> departmentList;

    @NotEmpty(message = "Geography list is required.")
    private List<GeographyDTO> geographyList;

    @NotBlank(message = "Document Name Is Required.")
    private String documentName;

    @NotBlank(message = "Document Type Is Required.")
    private String documentType;

    @NotBlank(message = "Document abbreviation is required.")
    private String documentAbbreviation;

    @NotBlank(message = "Level is Required.")
    private String level;

}
