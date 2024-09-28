package com.eroom.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SignInRespDTO {

    private Long userId;

    private List<String> roles ;

    private String username;

    private String token;

    private List<MenuSubMenuDataDTO> menuSubMenuDataDTOS;
}
