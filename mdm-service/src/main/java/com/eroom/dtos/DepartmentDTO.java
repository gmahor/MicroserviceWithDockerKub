package com.eroom.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DepartmentDTO {

    @NotEmpty(message = "Department code is required")
    private String departmentCode;

    @NotEmpty(message = "Department desc is required")
    private String departmentDesc;

}
