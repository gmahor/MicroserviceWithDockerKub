package com.eroom.dtos;

import java.util.Set;

public interface UserDetailRespDTO {

    Long getId();

    String getEmail();

    String getUsername();

    String getEmployeeName();

    Long getEmployeeMasterId();

    Set<RoleMappedWithUserRespDTO> getRoles();

    Set<DepartmentMappedWithUserRespDTO> getUserMappingWithDepartment();

    Set<DivisionMappedWithUserRespDTO> getUserMappingWithDivision();

    Set<GeographyMappedWithUserRespDTO> getUserMappingWithGeography();

    Set<SupplyVerticalMappedWithUserRespDTO> getUserMappingWithSupplyVertical();

}
