package com.eroom.dtos;

import java.util.List;

public interface DocumentAssignmentRespDTO {

    Long getId();

    Long getDocumentTypeId();

    String getDocumentName();

    List<DivisionMappedWithDocAssignmentRespDTO> getDocumentAssignmentMappingWithDivisions();

    List<SupplyVerticalMappedWithDocAssignmentRespDTO> getDocumentAssignmentMappingWithSupplyVerticals();

    List<FuncUserMappedWithDocAssignmentRespDTO> getFunctionalUserMappingWithDocAssigment();

    List<CheckerUserMappedWithDocAssignmentRespDTO> getCheckerMappingWithDocAssignment();

    List<FinalApproverUserMappedWithDocAssignmentRespDTO> getFinalApproverMappingWithDocAssigment();

    List<ProjectUserMappedWithDocAssignmentRespDTO> getProjectManagerMappingWithDocAssigment();

    List<DepartmentMappedWithDocAssignmentRespDTO> getDepartmentMappingWithDocAssigment();
}
