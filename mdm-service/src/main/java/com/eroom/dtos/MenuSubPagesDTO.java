package com.eroom.dtos;


import com.eroom.entities.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MenuSubPagesDTO {

    private String createdBy;

    private String modifiedBy;

    private String url;

    private String name;

    private Long menuId;

    private Integer subMenuOrder;

    private String subMenuIcon;

    private Menu menu;

}