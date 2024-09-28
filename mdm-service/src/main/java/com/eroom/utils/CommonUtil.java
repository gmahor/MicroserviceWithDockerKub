package com.eroom.utils;

import com.eroom.constants.GenericConstants;
import com.eroom.constants.MessageConstant;
import com.eroom.dtos.JWTUserDetailsDTO;
import com.eroom.dtos.PageDTO;
import com.eroom.entities.User;
import com.eroom.repositories.UserRepository;
import com.eroom.services.ModelMapperService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Component
public class CommonUtil {

    private final ResponseHandler responseHandler;

    private final HttpServletRequest servletContext;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final ModelMapperService modelMapper;


    public ResponseEntity<Object> requestValidation(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            errorMessage.append(error.getDefaultMessage()).append(". ");
        }
        log.info(MessageConstant.REQUEST_ERROR, errorMessage);
        return responseHandler.response("", errorMessage.toString(), false, HttpStatus.OK);
    }

    public PageDTO getDetailsPage(Object contentList, long size, Integer totalPages, long totalRecords) {
        PageDTO pageDTO = new PageDTO();
        pageDTO.setContent(contentList);
        pageDTO.setSize(size);
        pageDTO.setTotalPages(totalPages);
        pageDTO.setTotalRecords(totalRecords);
        return pageDTO;
    }

    public ResponseEntity<Object> checkUserIsValidOrNot() {
        String jwtToken = servletContext.getHeader("Authorization");
        if (jwtToken != null) {
            Claims claims = jwtUtil.getAllClaims(jwtToken.replace("Bearer",""));
            if (claims != null) {
                JWTUserDetailsDTO userDetails = modelMapper.map(claims.get("userDetails"), JWTUserDetailsDTO.class);
                if (userDetails != null) {
                    Optional<User> optionalUser = userRepository.findByIdAndStatus(userDetails.getUserId(),
                            GenericConstants.STATUS);
                    if (optionalUser.isEmpty()) {
                        log.info(MessageConstant.INVALID_USER_TOKEN);
                        return responseHandler.response("", MessageConstant.INVALID_USER_TOKEN, false, HttpStatus.OK);
                    }
                    return null;
                }
                log.info(MessageConstant.USER_DETAILS_NOT_FOUND_WITH_THIS_TOKEN);
                return responseHandler.response("", MessageConstant.USER_DETAILS_NOT_FOUND_WITH_THIS_TOKEN, false,
                        HttpStatus.OK);
            }
            log.info(MessageConstant.NO_CLAIMS_FOUND_IN_THIS_TOKEN);
            return responseHandler.response("", MessageConstant.NO_CLAIMS_FOUND_IN_THIS_TOKEN, false, HttpStatus.OK);
        }
        log.info(MessageConstant.TOKEN_NOT_FOUND_IN_HEADERS);
        return responseHandler.response("", MessageConstant.TOKEN_NOT_FOUND_IN_HEADERS, false, HttpStatus.OK);
    }

    public String loggedInUserName() {
        return servletContext.getHeader(GenericConstants.USERNAME);
    }

    public long loggedInUserId() {
        return Long.parseLong(servletContext.getHeader("id"));
    }

}
