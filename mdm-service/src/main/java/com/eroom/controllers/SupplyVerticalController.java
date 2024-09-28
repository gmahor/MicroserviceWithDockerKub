package com.eroom.controllers;

import com.eroom.constants.MessageConstant;
import com.eroom.constants.UrlConstants;
import com.eroom.dtos.AddSupplyVerticalDTO;
import com.eroom.dtos.PageDTO;
import com.eroom.dtos.UpdateSupplyVerticalDTO;
import com.eroom.services.SupplyVerticalService;
import com.eroom.utils.CommonUtil;
import com.eroom.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = UrlConstants.BASE_API_V1_SUPPLY_VERTICAL)
public class SupplyVerticalController {

    private final SupplyVerticalService supplyVerticalService;

    private final ResponseHandler responseHandler;

    private final CommonUtil commonUtil;

    @Autowired
    public SupplyVerticalController(SupplyVerticalService supplyVerticalService,
                                    ResponseHandler responseHandler,
                                    CommonUtil commonUtil) {
        this.supplyVerticalService = supplyVerticalService;
        this.responseHandler = responseHandler;
        this.commonUtil = commonUtil;
    }

    @PostMapping(value = UrlConstants.ADD_SUPPLY_VERTICAL)
    public ResponseEntity<Object> addSupplyVertical(@Validated @RequestBody AddSupplyVerticalDTO addSupplyVerticalDTO,
                                                    BindingResult bindingResult) {
        try {
            log.info("Add supply vertical request received by : {}", addSupplyVerticalDTO);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = supplyVerticalService.addSupplyVertical(addSupplyVerticalDTO);
                if (msg.equals(MessageConstant.SUPPLY_VERTICAL_SAVE_SUCCESS)) {
                    log.info(msg);
                    return responseHandler.response("", msg, true, HttpStatus.OK);
                }
                log.info(msg);
                return responseHandler.response("", msg, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_ADDING_SUPPLY_VERTICAL, e);
        }
        log.info(MessageConstant.ERROR_WHILE_ADDING_SUPPLY_VERTICAL);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_ADDING_SUPPLY_VERTICAL, false, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = UrlConstants.GET_ALL_SUPPLY_VERTICAL)
    public ResponseEntity<Object> getAllSupplyVerticals(@RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "50") Integer size,
                                                        @RequestParam(name = "searchSupplyVertical", required = false) String searchSupplyVertical) {
        try {
            log.info("Get all supply verticals request received by : pageNo : {}, size : {}, searchDivisionCode : {}", pageNo, size, searchSupplyVertical);
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                PageDTO allSupplyVerticals = supplyVerticalService.getAllSupplyVerticals(pageNo, size, searchSupplyVertical);
                if (allSupplyVerticals.getTotalRecords() > 0) {
                    log.info(MessageConstant.ALL_SUPPLY_VERTICAL_FETCHED_SUCCESS);
                    return responseHandler.response(allSupplyVerticals, MessageConstant.ALL_SUPPLY_VERTICAL_FETCHED_SUCCESS, true, HttpStatus.OK);
                }
                log.info(MessageConstant.DATA_NOT_FOUND);
                return responseHandler.response("", MessageConstant.DATA_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_FETCHING_ALL_SUPPLY_VERTICALS, e);
        }
        log.info(MessageConstant.ERROR_WHILE_FETCHING_ALL_SUPPLY_VERTICALS);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_FETCHING_ALL_SUPPLY_VERTICALS, false, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = UrlConstants.UPDATE_SUPPLY_VERTICAL)
    public ResponseEntity<Object> updateSupplyVertical(@Validated @RequestBody UpdateSupplyVerticalDTO updateSupplyVerticalDTO,
                                                       BindingResult bindingResult) {
        try {
            log.info("Update supply vertical request received by : {}", updateSupplyVerticalDTO);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = supplyVerticalService.updateSupplyVertical(updateSupplyVerticalDTO);
                if (msg.equals(MessageConstant.SUPPLY_VERTICAL_UPDATED_SUCCESS)) {
                    log.info(msg);
                    return responseHandler.response("", msg, true, HttpStatus.OK);
                }
                log.info(msg);
                return responseHandler.response("", msg, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_UPDATING_SUPPLY_VERTICAL, e);
        }
        log.info(MessageConstant.ERROR_WHILE_UPDATING_SUPPLY_VERTICAL);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_UPDATING_SUPPLY_VERTICAL, false, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = UrlConstants.DELETE_SUPPLY_VERTICAL)
    public ResponseEntity<Object> deleteSupplyVerticals(@RequestParam(name = "id") Long id) {
        try {
            log.info("Delete supply verticals request received by - id : {}", id);
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = supplyVerticalService.deleteSupplyVertical(id);
                if (msg.equals(MessageConstant.SUPPLY_VERTICAL_DELETED_SUCCESS)) {
                    log.info(msg);
                    return responseHandler.response("", msg, true, HttpStatus.OK);
                }
                log.info(msg);
                return responseHandler.response("", msg, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_DELETING_SUPPLY_VERTICAL, e);
        }
        log.error(MessageConstant.ERROR_WHILE_DELETING_SUPPLY_VERTICAL);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_DELETING_SUPPLY_VERTICAL, false, HttpStatus.BAD_REQUEST);
    }
}