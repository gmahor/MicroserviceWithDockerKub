package com.eroom.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AddDocumentAssignmentDTO {

    @NotEmpty(message = "Division List Is Required.")
    private List<DivisionDTO> divisionList;

    @NotEmpty(message = "Supply Vertical List Is Required.")
    private List<SupplyVerticalDTO> supplyVerticalList;

    @NotBlank(message = "Document Name Is Required.")
    private String documentName;

    @Min(value = 1, message = "Document Type ID Is Required.")
    private Long documentTypeId;

    private String status;

    private List<CheckerMapDTO> checkerMapDTOList;

    private List<ApproverMapDTO> approverMapDTOList;

    private List<FunctionalUserMapDTO> functionalUserMapDTOList;

    private List<ProjectManagerMapDTO> projectManagerMapDTOList;

    private List<DepartmentDTO> departmentList;

}
