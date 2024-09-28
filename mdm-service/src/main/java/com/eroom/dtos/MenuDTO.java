package com.eroom.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MenuDTO {

    private String createdBy;

    private String modifiedBy;

    private String name;

    private String isDropDown;

    private Integer menuOrder;

    private String menuIcon;
}