package com.eroom.controllers;

import com.eroom.constants.MessageConstant;
import com.eroom.constants.UrlConstants;
import com.eroom.dtos.PageDTO;
import com.eroom.services.GeographicalTerritoryService;
import com.eroom.utils.CommonUtil;
import com.eroom.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = UrlConstants.BASE_API_V1_GEOGRAPHICAL_TERRITORY)
public class GeographicalTerritoryController {

    private final ResponseHandler responseHandler;

    private final CommonUtil commonUtil;

    private final GeographicalTerritoryService geographicalTerritoryService;

    @Autowired
    public GeographicalTerritoryController(ResponseHandler responseHandler,
                                           CommonUtil commonUtil,
                                           GeographicalTerritoryService geographicalTerritoryService) {
        this.responseHandler = responseHandler;
        this.commonUtil = commonUtil;
        this.geographicalTerritoryService = geographicalTerritoryService;
    }

    @GetMapping(value = UrlConstants.GET_ALL_GEO_GRAPHICAL_TERRITORIES)
    public ResponseEntity<Object> getAllGeographicalTerritory(@RequestParam(required = false) Integer pageNo,
                                                              @RequestParam(required = false) Integer size) {

        try {
            log.info("Get all division request received by - pageNo : {}, size : {}", pageNo, size);
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                PageDTO allGeographicalTerritories = geographicalTerritoryService.getAllGeographicalTerritory(pageNo, size);
                if (allGeographicalTerritories.getSize() > 0) {
                    log.info(MessageConstant.ALL_GEO_GRAPHICAL_TERRITORY_FETCH_SUCCESS);
                    return responseHandler.response(allGeographicalTerritories, MessageConstant.ALL_GEO_GRAPHICAL_TERRITORY_FETCH_SUCCESS, true, HttpStatus.OK);
                }
                log.info(MessageConstant.GEO_GRAPHICAL_TERRITORY_NOT_FOUND);
                return responseHandler.response("", MessageConstant.GEO_GRAPHICAL_TERRITORY_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_FETCHING_GEO_GRAPHICAL_TERRITORY, e);
        }
        log.info(MessageConstant.ERROR_FETCHING_GEO_GRAPHICAL_TERRITORY);
        return responseHandler.response("", MessageConstant.ERROR_FETCHING_GEO_GRAPHICAL_TERRITORY, false, HttpStatus.BAD_REQUEST);
    }
}