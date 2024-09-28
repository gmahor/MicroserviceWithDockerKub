package com.eroom.controllers;

import com.eroom.constants.MessageConstant;
import com.eroom.constants.UrlConstants;
import com.eroom.dtos.*;
import com.eroom.services.UserService;
import com.eroom.utils.CommonUtil;
import com.eroom.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = UrlConstants.BASE_API_V1_USER)
public class UserController {

    private final UserService userService;

    private final CommonUtil commonUtil;

    private final ResponseHandler responseHandler;

    @Autowired
    public UserController(UserService userService, CommonUtil commonUtil, ResponseHandler responseHandler) {
        this.userService = userService;
        this.commonUtil = commonUtil;
        this.responseHandler = responseHandler;
    }

    @PostMapping(path = UrlConstants.ADD_USER)
    public ResponseEntity<Object> addUsersDetails(@Validated @RequestBody AddUserDTO addUserDto,
                                                  BindingResult bindingResult) {
        try {
            log.info("Add addUserDto request received by : {}", addUserDto);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = userService.addUsersDetails(addUserDto);
                if (msg.equals(MessageConstant.USER_ADDED_SUCCESS)) {
                    log.info(MessageConstant.USER_ADDED_SUCCESS);
                    return responseHandler.response("", MessageConstant.USER_ADDED_SUCCESS, true, HttpStatus.OK);
                }
                if (msg.equals(MessageConstant.USER_NAME_ALREAD_EXIST)) {
                    return responseHandler.response("", MessageConstant.USER_NAME_ALREAD_EXIST, false, HttpStatus.OK);
                }
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_ADDING_USER, e);
        }
        log.info(MessageConstant.ERROR_ADDING_USER);
        return responseHandler.response("", MessageConstant.ERROR_ADDING_USER, false, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = UrlConstants.GET_ALL_USER)
    public ResponseEntity<Object> getUserDetails(@RequestParam(defaultValue = "0") Integer pageNo,
                                                 @RequestParam(defaultValue = "100") Integer size) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                PageDTO pageDto = userService.getUserDetails(pageNo, size);
                if (pageDto.getTotalRecords() > 0) {
                    log.info(MessageConstant.GET_USER_DETAILS);
                    return responseHandler.response(pageDto, MessageConstant.GET_USER_DETAILS, true, HttpStatus.OK);
                } else {
                    log.info(MessageConstant.NO_DATA_FOUND);
                    return responseHandler.response("", MessageConstant.NO_DATA_FOUND, false, HttpStatus.OK);
                }

            }
            return isValidOrNot;

        } catch (

                Exception e) {
            log.error(MessageConstant.ERROR_WHILE_GET_USER_DETAILS, e);

        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_USER_DETAILS, false,
                HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping(path = UrlConstants.DELETE_USER_DETAILS)
    public ResponseEntity<Object> deleteUserDetails(@RequestParam(value = "userId") Long userId) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = userService.deleteUserDetails(userId);
                if (msg.equals(MessageConstant.USER_DELETED)) {
                    log.info(MessageConstant.USER_DELETED);
                    return responseHandler.response("", MessageConstant.USER_DELETED, true, HttpStatus.OK);
                }
                log.info(msg);
                return responseHandler.response("", msg, false, HttpStatus.BAD_REQUEST);
            }

            return isValidOrNot;

        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_DELETE_USER, e);

        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_DELETE_USER, false, HttpStatus.OK);

    }

    @PutMapping(path = UrlConstants.USER_DETAILS_UPDATE)
    public ResponseEntity<Object> userDetailsUpdate(@Validated @RequestBody UserUpdateRespondDTO userUpdateRespondDto,
                                                    BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = userService.userDetailsUpdate(userUpdateRespondDto);
                if (msg.equals(MessageConstant.USER_DETAILS_UPDATED)) {
                    log.info(msg);
                    return responseHandler.response(msg, MessageConstant.USER_DETAILS_UPDATED, true, HttpStatus.OK);
                } else {
                    log.info(msg);
                    return responseHandler.response(msg, MessageConstant.USER_DETAILS_NOT_FOUND_WITH_REQUEST_ID, false,
                            HttpStatus.OK);
                }

            }
            return isValidOrNot;

        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_UPDATE_USER_DETAILS, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_UPDATE_USER_DETAILS, false,
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = UrlConstants.GET_USER_DETAILS_BY_ID)
    public ResponseEntity<Object> getUserDetailsById(@RequestParam(value = "userId") Long userId) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                UserDetailRespDTO userDetails = userService.getUserDetailsById(userId);
                if (userDetails != null) {
                    log.info(MessageConstant.GET_USER_DETAILS);
                    return responseHandler.response(userDetails, MessageConstant.GET_USER_DETAILS, true, HttpStatus.OK);
                }
                log.info(MessageConstant.DATA_NOT_FOUND);
                return responseHandler.response("", MessageConstant.DATA_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(e.toString());
        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_USER_DETAILS, false,
                HttpStatus.BAD_REQUEST);

    }

    @GetMapping(path = UrlConstants.GET_FUNCTIONAL_USER_LIST)
    ResponseEntity<Object> getFunctionalUserList(
            @RequestParam(name = "departments") List<String> departments,
            @RequestParam(name = "divisions") List<String> divisions,
            @RequestParam(name = "supplyVerticals") List<String> supplierVerticals) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                List<FunctionalUserMapUsers> functionalUserList = userService.getFunctionalUserList(departments, divisions, supplierVerticals);
                if (!functionalUserList.isEmpty()) {
                    log.info(MessageConstant.GET_FUNCTION_USER_lIST);
                    return responseHandler.response(functionalUserList, MessageConstant.GET_FUNCTION_USER_lIST, true,
                            HttpStatus.OK);
                }
                return responseHandler.response("", MessageConstant.DATA_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_GET_FUNCTIONAL_USER, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_FUNCTIONAL_USER, false,
                HttpStatus.BAD_REQUEST);

    }

    @GetMapping(path = UrlConstants.GET_APPROVER_USER_LIST)
    ResponseEntity<Object> getAppoverUserList(
            @RequestParam(name = "departments") List<String> departments,
            @RequestParam(name = "divisions") List<String> divisions,
            @RequestParam(name = "supplyVerticals") List<String> supplierVerticals) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                List<ApproverUserMapUsers> approverUserList = userService.getAppoverUserList(departments, divisions, supplierVerticals);
                if (!approverUserList.isEmpty()) {
                    log.info(MessageConstant.GET_APPROVER_USER_lIST);
                    return responseHandler.response(approverUserList, MessageConstant.GET_APPROVER_USER_lIST, true,
                            HttpStatus.OK);
                }
                return responseHandler.response("", MessageConstant.DATA_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_GET_APPROVER_USER, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_APPROVER_USER, false,
                HttpStatus.BAD_REQUEST);

    }

    @GetMapping(path = UrlConstants.GET_CHECKER_USER_LIST)
    ResponseEntity<Object> getCheckerUserList(
            @RequestParam(name = "departments") List<String> departments,
            @RequestParam(name = "divisions") List<String> divisions,
            @RequestParam(name = "supplyVerticals") List<String> supplierVerticals) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                List<CheckerUserMapUsers> checkerUserList = userService.getCheckerUserList(departments, divisions, supplierVerticals);
                if (!checkerUserList.isEmpty()) {
                    log.info(MessageConstant.GET_CHECKER_USER_lIST);
                    return responseHandler.response(checkerUserList, MessageConstant.GET_CHECKER_USER_lIST, true,
                            HttpStatus.OK);
                }
                return responseHandler.response("", MessageConstant.DATA_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_GET_CHECKER_USER, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_CHECKER_USER, false,
                HttpStatus.BAD_REQUEST);

    }

    @GetMapping(path = UrlConstants.GET_PROJECT_MANAGER_LIST)
    ResponseEntity<Object> getProjectManagerList(
            @RequestParam(name = "departments") List<String> departments,
            @RequestParam(name = "divisions") List<String> divisions,
            @RequestParam(name = "supplyVerticals") List<String> supplierVerticals) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                List<ProjectManagerMappingWithUsers> projectManagerList = userService.getProjectManagerList(departments, divisions, supplierVerticals);
                if (!projectManagerList.isEmpty()) {
                    log.info(MessageConstant.GET_PROJECT_MANAGER_LIST);
                    return responseHandler.response(projectManagerList, MessageConstant.GET_PROJECT_MANAGER_LIST, true,
                            HttpStatus.OK);
                }
                return responseHandler.response("", MessageConstant.DATA_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_GET_PROJECT_MANAGER, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_PROJECT_MANAGER, false,
                HttpStatus.BAD_REQUEST);
    }

}