package com.eroom.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserUpdateRespondDTO {

    private String password;

    private String email;

    private String username;

    private String employeeName;

    private Long userId;

    private Long employeeMasterId;

    private List<DivisionDTO> divisionList;

    private List<SupplyVerticalDTO> supplyVerticalList;

    private List<GeographyDTO> geographyList;

    private List<DepartmentDTO> departmentList;

    private List<RoleMapUserDTO> roleList;
}
