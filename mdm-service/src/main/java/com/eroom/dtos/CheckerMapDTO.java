package com.eroom.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CheckerMapDTO {

    @NotEmpty(message = "checker  is required")
    private String checker;

    private Long checkerId;

}
