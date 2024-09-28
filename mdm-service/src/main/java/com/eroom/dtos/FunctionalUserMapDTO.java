package com.eroom.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FunctionalUserMapDTO {

    @NotEmpty(message = "functional User  is required")
    private String funcationUser;

    private Long functionalUserId;

}
