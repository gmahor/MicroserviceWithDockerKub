package com.eroom.dtos;

import lombok.Data;

@Data
public class MapRoleMenuSubPagesDTO {

    private String menuName;

    private String menuSubPageName;

    private String isDropDown;

    private String menuSubPageUrl;

    private String roleName;

    private Integer menuOrder;

    private Integer subMenuOrder;

    private String subMenuIcon;

    private String menuIcon;

}
