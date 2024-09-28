package com.eroom.controllers;

import com.eroom.constants.MessageConstant;
import com.eroom.constants.UrlConstants;
import com.eroom.dtos.DepartmentMappingDTO;
import com.eroom.dtos.EmployeeRespondDTO;
import com.eroom.dtos.PageDTO;
import com.eroom.entities.Employee;
import com.eroom.services.EmployeeService;
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

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = UrlConstants.BASE_API_V1_EMPLOYEE)
public class EmployeeController {

    private final EmployeeService employeeService;

    private final CommonUtil commonUtil;

    private final ResponseHandler responseHandler;

    @Autowired
    public EmployeeController(EmployeeService employeeService, CommonUtil commonUtil, ResponseHandler responseHandler) {
        this.employeeService = employeeService;
        this.commonUtil = commonUtil;
        this.responseHandler = responseHandler;
    }

    @GetMapping(value = UrlConstants.GET_ALL_EMPLOYEES)
    public ResponseEntity<Object> getAllEmployees(@RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "50") Integer size) {
        try {
            log.info("Get all employees request received by - pageNo : {}, size : {}", pageNo, size);
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                PageDTO employees = employeeService.getAllEmployees(pageNo, size);
                if (employees.getTotalRecords() > 0) {
                    log.info(MessageConstant.ALL_EMPLOYEES_FETCH_SUCCESS);
                    return responseHandler.response(employees, MessageConstant.ALL_EMPLOYEES_FETCH_SUCCESS, true,
                            HttpStatus.OK);
                }
                log.info(MessageConstant.EMPLOYEE_NOT_FOUND);
                return responseHandler.response("", MessageConstant.EMPLOYEE_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_FETCHING_EMPLOYEES, e);
        }
        log.info(MessageConstant.ERROR_FETCHING_EMPLOYEES);
        return responseHandler.response("", MessageConstant.ERROR_FETCHING_EMPLOYEES, false, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = UrlConstants.GET_EMPLOYEE_NUMBER_LIST)
    ResponseEntity<Object> getEmployeeNumberList() {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                List<EmployeeRespondDTO> employeeList = employeeService.getEmployeeNumberList();
                if (!employeeList.isEmpty()) {
                    log.info(MessageConstant.GET_ALL_EMPLOYEE_NUMBER);
                    return responseHandler.response(employeeList, MessageConstant.GET_ALL_EMPLOYEE_NUMBER, true,
                            HttpStatus.OK);
                }
                log.info(MessageConstant.DATA_NOT_FOUND);
                return responseHandler.response("", MessageConstant.DATA_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_GET_EMPLOYEE_NUMBER);
        }
        log.info(MessageConstant.ERROR_WHILE_GET_EMPLOYEE_NUMBER);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_EMPLOYEE_NUMBER, false,
                HttpStatus.BAD_REQUEST);

    }

    @GetMapping(path = UrlConstants.GET_EMPLOYEE_USER_NAME)
    ResponseEntity<Object> getEmployeeUsername(@RequestParam("employeeId") String employeeId) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                Optional<Employee> employeeUsername = employeeService.getEmployeeUsername(employeeId);
                if (employeeUsername.isPresent()) {
                    log.info(MessageConstant.GET_EMPLOYEE_USER);
                    return responseHandler.response(employeeUsername.get(), MessageConstant.GET_EMPLOYEE_USER, true,
                            HttpStatus.OK);
                }
                log.info(MessageConstant.DATA_NOT_FOUND);
                return responseHandler.response("", MessageConstant.DATA_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_GET_EMPLOYEE_USER);
        }
        log.info(MessageConstant.ERROR_WHILE_GET_EMPLOYEE_USER);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_EMPLOYEE_USER, false,
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = UrlConstants.GET_DEPARTMENT_LIST)
    ResponseEntity<Object> getDepartmentList() {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                List<DepartmentMappingDTO> departmentList = employeeService.getDepartmentList();
                if (!departmentList.isEmpty()) {
                    log.info(MessageConstant.GET_DEPARTMENT_LIST);
                    return responseHandler.response(departmentList, MessageConstant.GET_EMPLOYEE_USER, true,
                            HttpStatus.OK);
                }
                log.info(MessageConstant.DATA_NOT_FOUND);
                return responseHandler.response("", MessageConstant.DATA_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_GET_DEPARTMENT_LIST);
        }
        log.info(MessageConstant.ERROR_WHILE_GET_DEPARTMENT_LIST);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_DEPARTMENT_LIST, false,
                HttpStatus.BAD_REQUEST);
    }

}
