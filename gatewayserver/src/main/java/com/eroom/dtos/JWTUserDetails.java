package com.eroom.dtos;

import lombok.Data;

@Data
public class JWTUserDetails {

    private Long userId;

    private String username;

    private String roles;
}