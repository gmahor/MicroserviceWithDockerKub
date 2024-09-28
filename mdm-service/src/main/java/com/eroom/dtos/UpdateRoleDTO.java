package com.eroom.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRoleDTO {

    @Min(value = 1, message = "Role Id Is Required.")
    private Long roleId;

    @NotBlank(message = "Role Name Is Required.")
    private String roleName;

}