package com.eroom.dtos;

import lombok.Data;

import java.util.List;

@Data
public class MenuSubMenuDataDTO {

    private String menuName;

    private Integer menuOrder;

    private String isDropDown;

    private Long manuSubPageId;

    private String menuIcon;

    private List<MapRoleMenuSubPagesDTO> subMenuList;



}
