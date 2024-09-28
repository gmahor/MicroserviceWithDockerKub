package com.eroom.controllers;


import com.eroom.constants.MessageConstant;
import com.eroom.constants.UrlConstants;
import com.eroom.dtos.PageDTO;
import com.eroom.services.DivisionService;
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
@RequestMapping(path = UrlConstants.BASE_API_V1_DIVISION)
public class DivisionController {

	private final DivisionService divisionService;

	private final CommonUtil commonUtil;

	private final ResponseHandler responseHandler;

	@Autowired
	public DivisionController(DivisionService divisionService, CommonUtil commonUtil, ResponseHandler responseHandler) {
		this.divisionService = divisionService;
		this.commonUtil = commonUtil;
		this.responseHandler = responseHandler;
	}

	@GetMapping(value = UrlConstants.GET_ALL_DIVISIONS)
	public ResponseEntity<Object> getAllDivision(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "50000") Integer size) {
		try {
			log.info("Get all division request received by - pageNo : {}, size : {}", pageNo, size);
			ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
			if (isValidOrNot == null) {
				PageDTO allDivision = divisionService.getAllDivision(pageNo, size);
				if (allDivision.getTotalRecords() > 0) {
					log.info(MessageConstant.ALL_DIVISION_FETCH_SUCCESS);
					return responseHandler.response(allDivision, MessageConstant.ALL_DIVISION_FETCH_SUCCESS, true,
							HttpStatus.OK);
				}
				log.info(MessageConstant.DIVISION_NOT_FOUND);
				return responseHandler.response("", MessageConstant.DIVISION_NOT_FOUND, false, HttpStatus.OK);
			}
			return isValidOrNot;
		} catch (Exception e) {
			log.error(MessageConstant.ERROR_FETCHING_DIVISIONS, e);
		}
		log.info(MessageConstant.ERROR_FETCHING_DIVISIONS);
		return responseHandler.response("", MessageConstant.ERROR_FETCHING_DIVISIONS, false, HttpStatus.BAD_REQUEST);
	}

}
