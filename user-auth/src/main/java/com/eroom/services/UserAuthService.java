package com.eroom.services;

import com.eroom.dtos.SignInDTO;

public interface UserAuthService {

    Object signIn(SignInDTO signInDTO);

    String logout(long id);
}
