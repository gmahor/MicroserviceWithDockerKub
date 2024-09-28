package com.eroom.controllers;

import com.eroom.constants.MessageConstant;
import com.eroom.constants.UrlConstants;
import com.eroom.dtos.AddRoleDTO;
import com.eroom.dtos.UpdateRoleDTO;
import com.eroom.entities.Role;
import com.eroom.services.RoleService;
import com.eroom.utils.CommonUtil;
import com.eroom.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = UrlConstants.BASE_API_V1_ROLE)
public class RoleController {

	private final RoleService roleService;

	private final CommonUtil commonUtil;

	private final ResponseHandler responseHandler;

	@Autowired
	public RoleController(RoleService roleService, CommonUtil commonUtil, ResponseHandler responseHandler) {
		this.roleService = roleService;
		this.commonUtil = commonUtil;
		this.responseHandler = responseHandler;
	}

	@PostMapping(value = UrlConstants.ADD_ROLE)
	public ResponseEntity<Object> addRole(@RequestBody AddRoleDTO addRoleDTO, BindingResult bindingResult) {
		try {
			log.info("Add role request received by : {}", addRoleDTO);
			if (bindingResult.hasErrors()) {
				return commonUtil.requestValidation(bindingResult);
			}
			ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
			if (isValidOrNot == null) {
				String msg = roleService.addRole(addRoleDTO);
				if (msg.equals(MessageConstant.ROLE_ADDED_SUCCESS)) {
					log.info(MessageConstant.ROLE_ADDED_SUCCESS);
					return responseHandler.response("", MessageConstant.ROLE_ADDED_SUCCESS, true, HttpStatus.OK);
				}
				if (msg.equals(MessageConstant.ROLE_NAME_ALREADY_EXIST)) {
					log.info(msg);
					return responseHandler.response("", MessageConstant.ROLE_NAME_ALREADY_EXIST, false, HttpStatus.OK);

				}
			}
			return isValidOrNot;
		} catch (Exception e) {
			log.error(MessageConstant.ERROR_ADDING_ROLE, e);
		}
		log.info(MessageConstant.ERROR_ADDING_ROLE);
		return responseHandler.response("", MessageConstant.ERROR_ADDING_ROLE, false, HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = UrlConstants.GET_ALL_ROLES)
	public ResponseEntity<Object> getAllRoles() {
		try {
			log.info("Get all roles request received");
			ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
			if (isValidOrNot == null) {
				Set<Role> allRoles = roleService.getAllRoles();
				if (!allRoles.isEmpty()) {
					log.info(MessageConstant.ALL_ROLE_FETCHED_SUCCESS);
					return responseHandler.response(allRoles, MessageConstant.ALL_ROLE_FETCHED_SUCCESS, true, HttpStatus.OK);
				}
				log.info(MessageConstant.NO_DATA_FOUND);
				return responseHandler.response("", MessageConstant.NO_DATA_FOUND, false, HttpStatus.OK);
			}
			return isValidOrNot;
		} catch (Exception e) {
			log.error(MessageConstant.ERROR_FETCHING_ALL_ROLE, e);
		}
		log.info(MessageConstant.ERROR_FETCHING_ALL_ROLE);
		return responseHandler.response("", MessageConstant.ERROR_FETCHING_ALL_ROLE, false, HttpStatus.BAD_REQUEST);
	}

	@PutMapping(value = UrlConstants.UPDATE_ROLE)
	public ResponseEntity<Object> updateRole(@Validated @RequestBody UpdateRoleDTO updateRoleDTO, BindingResult bindingResult) {
		try {
			log.info("Update role request received by : {}", updateRoleDTO);
			if (bindingResult.hasErrors()) {
				return commonUtil.requestValidation(bindingResult);
			}
			ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
			if (isValidOrNot == null) {
				String msg = roleService.updateRole(updateRoleDTO);
				if (msg.equals(MessageConstant.ROLE_UPDATED_SUCCESS)) {
					log.info(MessageConstant.ROLE_UPDATED_SUCCESS);
					return responseHandler.response("", MessageConstant.ROLE_UPDATED_SUCCESS, true, HttpStatus.OK);
				}
				log.info(MessageConstant.ROLE_NOT_FOUND);
				return responseHandler.response("", MessageConstant.ROLE_NOT_FOUND, false, HttpStatus.OK);
			}
			return isValidOrNot;
		} catch (Exception e) {
			log.error(MessageConstant.ERROR_UPDATING_ROLE);
		}
		log.info(MessageConstant.ERROR_UPDATING_ROLE);
		return responseHandler.response("", MessageConstant.ERROR_UPDATING_ROLE, false, HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping(value = UrlConstants.DELETE_ROLE)
	public ResponseEntity<Object> deleteRole(@RequestParam("id") Long id) {
		try {
			log.info("Delete role request received by id : {}", id);
			ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
			if (isValidOrNot == null) {
				String msg = roleService.deleteRole(id);
				if (msg.equals(MessageConstant.ROLE_DELETED_SUCCESS)) {
					log.info(msg);
					return responseHandler.response("", msg, true, HttpStatus.OK);
				}
				log.info(msg);
				return responseHandler.response("", msg, false, HttpStatus.BAD_REQUEST);
			}
			return isValidOrNot;
		} catch (Exception e) {
			log.error(MessageConstant.ERROR_DELETING_ROLE);
		}
		log.info(MessageConstant.ERROR_DELETING_ROLE);
		return responseHandler.response("", MessageConstant.ERROR_DELETING_ROLE, false, HttpStatus.BAD_REQUEST);
	}

}
