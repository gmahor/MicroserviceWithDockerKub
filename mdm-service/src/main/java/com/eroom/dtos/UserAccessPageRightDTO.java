package com.eroom.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class UserAccessPageRightDTO {

    @Min(value = 1, message = "Role Id Required.")
    private Long roleId;

    @NotEmpty(message = "Menu Ids Required.")
    private List<Long> menuIdList;

}