package com.eroom.dtos;

import lombok.Data;

@Data
public class JWTUserDetailsDTO {

    private Long userId;

    private String username;
}