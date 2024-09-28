package com.eroom.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddRoleDTO {

    @NotBlank(message = "Role Name Is Required.")
    private String roleName;

}
