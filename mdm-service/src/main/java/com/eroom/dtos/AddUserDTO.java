package com.eroom.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AddUserDTO {

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Employee name is required")
    private String employeeName;

    @Min(value = 1, message = "Employee master  ID Is Required.")
    private Long employeeMasterId;

    @NotEmpty(message = "Division list is required.")
    private List<DivisionDTO> divisionList;

    @NotEmpty(message = "Supply vertical list is required.")
    private List<SupplyVerticalDTO> supplyVerticalList;

    @NotEmpty(message = "Geography list is required.")
    private List<GeographyDTO> geographyList;

    @NotEmpty(message = "Department list is required.")
    private List<DepartmentDTO> departmentList;

    @NotEmpty(message = "Role list is required.")
    private List<RoleMapUserDTO> roleList;

}
