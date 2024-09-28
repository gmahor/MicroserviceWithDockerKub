package com.eroom.controllers;

import com.eroom.constants.MessageConstant;
import com.eroom.constants.UrlConstants;
import com.eroom.dtos.MenuDataDTO;
import com.eroom.dtos.PageDTO;
import com.eroom.dtos.UserAccessPageRightDTO;
import com.eroom.dtos.UserAccessPageRightUpdateDTO;
import com.eroom.services.PageAccessService;
import com.eroom.utils.CommonUtil;
import com.eroom.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = UrlConstants.BASE_API_V1_MENU_AND_PAGE)
public class PageAccessController {

    private final PageAccessService pageAccessService;

    private final CommonUtil commonUtil;

    private final ResponseHandler responseHandler;

    @Autowired
    public PageAccessController(CommonUtil commonUtil, PageAccessService pageAccessService,
                                ResponseHandler responseHandler) {
        this.pageAccessService = pageAccessService;
        this.commonUtil = commonUtil;
        this.responseHandler = responseHandler;
    }

    @PostMapping(value = UrlConstants.ADD_PAGE_ACCESS_RIGHT)
    public ResponseEntity<Object> addPageAccessRight(@RequestBody UserAccessPageRightDTO userAccessPageRightDTO,
                                                     BindingResult bindingResult) {
        try {
            log.info("Add page access right request received: {}", userAccessPageRightDTO);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = pageAccessService.addPageAccessRight(userAccessPageRightDTO);
                if (msg.equals(MessageConstant.PAGE_ACCESS_RIGHT_SAVED_SUCCESS)) {
                    log.info(msg);
                    return responseHandler.response("", msg, true, HttpStatus.OK);
                }
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_ADDING_PAGES_ACCESS_RIGHT, e);
        }
        log.info(MessageConstant.ERROR_WHILE_ADDING_PAGES_ACCESS_RIGHT);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_ADDING_PAGES_ACCESS_RIGHT, false,
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = UrlConstants.GET_MENU_SUB_PAGES_LIST)
    public ResponseEntity<Object> getMenuSubPagesList() {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                List<MenuDataDTO> menuSubPagesList = pageAccessService.getMenuSubPagesList();
                if (!menuSubPagesList.isEmpty()) {
                    log.info(MessageConstant.GET_ACCESS_PAGES);
                    return responseHandler.response(menuSubPagesList, MessageConstant.GET_ACCESS_PAGES, true,
                            HttpStatus.OK);
                }
                log.info(MessageConstant.DATA_NOT_FOUND);
                return responseHandler.response("", MessageConstant.DATA_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_ACCESS_PAGE, e);
        }
        log.info(MessageConstant.ERROR_WHILE_ACCESS_PAGE);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_ACCESS_PAGE, false, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = UrlConstants.GET_PAGES_LIST_WITH_ROLES)
    ResponseEntity<Object> getMenuAndSubMenuPagesWithRole(@RequestParam(defaultValue = "0") Integer pageNo,
                                                          @RequestParam(defaultValue = "50") Integer size) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                PageDTO menuSubPagesListWithRole = pageAccessService
                        .getMenuAndSubMenuPagesWithRole(pageNo, size);
                if (menuSubPagesListWithRole.getTotalRecords() > 0) {
                    return responseHandler.response(menuSubPagesListWithRole, MessageConstant.GET_ACCESS_PAGES, true,
                            HttpStatus.OK);
                }
                log.info(MessageConstant.DATA_NOT_FOUND);
                return responseHandler.response("", MessageConstant.DATA_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;

        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_ACCESS_PAGES, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_ACCESS_PAGES, false, HttpStatus.BAD_REQUEST);

    }


    @DeleteMapping(path = UrlConstants.DELETE_MENU_AND_SUB_MENU_PAGE_BY_ROLE_ID)
    ResponseEntity<Object> deleteMenuAndSubMenuPageByRoleId(@RequestParam(value = "roleId") Long roleId) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = pageAccessService.deleteMenuAndSubMenuPageByRoleId(roleId);
                if (msg.equals(MessageConstant.DELETED_MENU_SUB_MENU_PAGE)) {
                    return responseHandler.response("", msg, true, HttpStatus.OK);
                }
                return responseHandler.response("", MessageConstant.ERROR_WHILE_DELETE_MENU_SUB_MENU_PAGES, false,
                        HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.info(MessageConstant.ERROR_WHILE_DELETE_MENU_SUB_MENU_PAGES, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_DELETE_MENU_SUB_MENU_PAGES, false,
                HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = UrlConstants.UPDATE_PAGE_ACCESS_RIGHT)
    public ResponseEntity<Object> updatePageAccessRight(
            @RequestBody UserAccessPageRightUpdateDTO userAccessPageRightUpdateDto, BindingResult bindingResult) {
        try {
            log.info("update page access right request received: {}", userAccessPageRightUpdateDto);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = pageAccessService.updatePageAccessRight(userAccessPageRightUpdateDto);
                if (msg.equals(MessageConstant.PAGE_ACCESS_RIGHT_UPDATED_SUCCESS)) {
                    log.info(msg);
                    return responseHandler.response("", msg, true, HttpStatus.OK);
                }
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_UPDATED_PAGES_ACCESS_RIGHT, e);
        }
        log.info(MessageConstant.ERROR_WHILE_UPDATED_PAGES_ACCESS_RIGHT);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_UPDATED_PAGES_ACCESS_RIGHT, false,
                HttpStatus.BAD_REQUEST);
    }

}
