package com.eroom.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInDTO {


    @NotBlank(message = "Password is required.")
    private String password;

    @NotBlank(message = "Username is required.")
    private String username;
}
