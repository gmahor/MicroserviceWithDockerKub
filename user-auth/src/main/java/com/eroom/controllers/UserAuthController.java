package com.eroom.controllers;

import com.eroom.constants.MessageConstant;
import com.eroom.constants.UrlConstants;
import com.eroom.dtos.SignInDTO;
import com.eroom.services.UserAuthService;
import com.eroom.utils.CommonUtil;
import com.eroom.utils.ResponseHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(UrlConstants.BASE_API_V1_AUTH)
public class UserAuthController {

    private final UserAuthService userAuthService;

    private final ResponseHandler responseHandler;

    private final CommonUtil commonUtil;


    @PostMapping(value = UrlConstants.SIGN_IN)
    public ResponseEntity<Object> signIn(@Validated @RequestBody SignInDTO signInDTO, BindingResult bindingResult) {
        try {
            log.info("SignIn request received by : {}", signInDTO);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            Object obj = userAuthService.signIn(signInDTO);
            if (MessageConstant.ERROR_WHILE_CREATING_TOKEN.equals(obj)) {
                log.info(MessageConstant.ERROR_WHILE_CREATING_TOKEN);
                return responseHandler.response("", MessageConstant.ERROR_WHILE_CREATING_TOKEN, false,
                        HttpStatus.BAD_REQUEST);
            } else if (MessageConstant.USER_NOT_FOUND_WITH_THIS_USERNAME.equals(obj)) {
                log.info(MessageConstant.USER_NOT_FOUND_WITH_THIS_USERNAME);
                return responseHandler.response("", MessageConstant.USER_NOT_FOUND_WITH_THIS_USERNAME, false,
                        HttpStatus.BAD_REQUEST);
            } else if (MessageConstant.UNAUTHORIZED.equals(obj)) {
                log.info(MessageConstant.UNAUTHORIZED);
                return responseHandler.response("", MessageConstant.UNAUTHORIZED, false, HttpStatus.BAD_REQUEST);

            }
            return responseHandler.response(obj, MessageConstant.SIGN_IN_SUCCESS, true, HttpStatus.OK);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_SIGN_IN, e);
        }
        log.info(MessageConstant.ERROR_WHILE_SIGN_IN);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_SIGN_IN, false, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = UrlConstants.LOGOUT)
    public ResponseEntity<Object> logout(@RequestParam(value = "id") long id) {
        try {
            log.info("Logout request received by user id : {}", id);
            String logout = userAuthService.logout(id);
            if (MessageConstant.USER_LOGOUT_SUCCESS.equals(logout)) {
                log.info(MessageConstant.USER_LOGOUT_SUCCESS);
                return responseHandler.response("", MessageConstant.USER_LOGOUT_SUCCESS, true, HttpStatus.OK);
            } else if (MessageConstant.USER_NOT_FOUND.equals(logout)) {
                log.info(MessageConstant.USER_NOT_FOUND);
                return responseHandler.response("", MessageConstant.USER_NOT_FOUND, true, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_LOGOUT_USER, e);
        }
        log.info(MessageConstant.ERROR_LOGOUT_USER);
        return responseHandler.response("", MessageConstant.ERROR_LOGOUT_USER, false, HttpStatus.BAD_REQUEST);
    }

}
