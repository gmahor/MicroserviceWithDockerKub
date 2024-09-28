package com.eroom.controllers;

import com.eroom.constants.MessageConstant;
import com.eroom.constants.UrlConstants;
import com.eroom.entities.Organization;
import com.eroom.services.OrganisationService;
import com.eroom.utils.CommonUtil;
import com.eroom.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = UrlConstants.BASE_API_V1_ORGANISATION)
public class OrganisationController {

	private final OrganisationService organisationService;

	private final CommonUtil commonUtil;

	private final ResponseHandler responseHandler;

	@Autowired
	public OrganisationController(OrganisationService organisationService, CommonUtil commonUtil,
			ResponseHandler responseHandler) {
		this.organisationService = organisationService;
		this.commonUtil = commonUtil;
		this.responseHandler = responseHandler;
	}

	@GetMapping(value = UrlConstants.GET_ALL_ORGANISATION)
	public ResponseEntity<Object> getAllOrganisation() {
		try {
			log.info("Get all getAllOrganisation request received");
			ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
			if (isValidOrNot == null) {
				List<Organization> organizationList = organisationService.organisationList();
				if (!organizationList.isEmpty()) {
					log.info(MessageConstant.ALL_ORGANISATION_FETCHED_SUCCESS);
					return responseHandler.response(organizationList, MessageConstant.ALL_ORGANISATION_FETCHED_SUCCESS,
							true, HttpStatus.OK);
				}
				log.info(MessageConstant.NO_DATA_FOUND);
				return responseHandler.response("", MessageConstant.NO_DATA_FOUND, false, HttpStatus.OK);
			}
			return isValidOrNot;
		} catch (Exception e) {
			log.error(MessageConstant.ERROR_FETCHING_ALL_ORGANISATION, e);
		}
		log.info(MessageConstant.ERROR_FETCHING_ALL_ORGANISATION);
		return responseHandler.response("", MessageConstant.ERROR_FETCHING_ALL_ORGANISATION, false,
				HttpStatus.BAD_REQUEST);
	}

}
