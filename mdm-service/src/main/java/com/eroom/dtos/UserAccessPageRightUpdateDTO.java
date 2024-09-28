package com.eroom.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class UserAccessPageRightUpdateDTO {

    @Min(value = 1, message = "Role Id Required.")
    private Long roleId;

    @NotEmpty(message = "Menu Name Are Required.")
    private List<String> menuNameList;

}