package com.eroom.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UpdateDocumentAssignmentDTO {

    private List<DivisionDTO> divisionList;

    private List<SupplyVerticalDTO> supplyVerticalList;

    private String isActive;

    private Long id;

    private List<CheckerMapDTO> checkerMapDTOList;

    private List<ApproverMapDTO> approverMapDTOList;

    private List<FunctionalUserMapDTO> functionalUserMapDTOList;

    private List<ProjectManagerMapDTO> projectManagerMapDTOList;

    private List<DepartmentDTO> departmentList;

}
