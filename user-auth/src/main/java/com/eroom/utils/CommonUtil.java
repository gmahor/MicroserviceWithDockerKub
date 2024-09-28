package com.eroom.utils;

import com.eroom.constants.MessageConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@AllArgsConstructor
@Slf4j
@Component
public class CommonUtil {

    private final ResponseHandler responseHandler;

    private final HttpServletRequest servletContext;


    public ResponseEntity<Object> requestValidation(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            errorMessage.append(error.getDefaultMessage()).append(". ");
        }
        log.info(MessageConstant.REQUEST_ERROR, errorMessage);
        return responseHandler.response("", errorMessage.toString(), false,
                HttpStatus.BAD_REQUEST);
    }
}
