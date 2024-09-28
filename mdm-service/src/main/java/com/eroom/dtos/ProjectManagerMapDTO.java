package com.eroom.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProjectManagerMapDTO {

    @NotEmpty(message = "Project Manager  is required")
    private String projectManager;

    private Long projectManagerId;

}
