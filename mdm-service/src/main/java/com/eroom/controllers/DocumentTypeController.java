package com.eroom.controllers;

import com.eroom.constants.MessageConstant;
import com.eroom.constants.UrlConstants;
import com.eroom.dtos.*;
import com.eroom.entities.DocumentTypeMappingWithDepartment;
import com.eroom.entities.DocumentTypeMappingWithDivision;
import com.eroom.entities.DocumentTypeMappingWithSupplyVertical;
import com.eroom.services.DocumentTypeService;
import com.eroom.utils.CommonUtil;
import com.eroom.utils.ResponseHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = UrlConstants.BASE_API_V1_DOCUMENT)
public class DocumentTypeController {

    private final CommonUtil commonUtil;

    private final ResponseHandler responseHandler;

    private final DocumentTypeService documentTypeService;


    @GetMapping(path = UrlConstants.GET_DOCUMENT_TYPE)
    ResponseEntity<Object> getDocumentType(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "50") Integer size) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                PageDTO getAllDocumentType = documentTypeService.getDocumentType(pageNo, size);
                if (getAllDocumentType.getTotalRecords() > 0) {
                    log.info(MessageConstant.ALL_DOCUMENT_TYPE);
                    return responseHandler.response(getAllDocumentType, MessageConstant.ALL_DOCUMENT_TYPE, true,
                            HttpStatus.OK);
                }
                log.info(MessageConstant.DOCUMENT_TYPE_NOT_FOUND);
                return responseHandler.response("", MessageConstant.DOCUMENT_TYPE_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_GET_DOCUMENT_TYPE, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_DOCUMENT_TYPE, false,
                HttpStatus.BAD_REQUEST);

    }

    @PostMapping(path = UrlConstants.ADD_DOCUMENT_TYPE)
    public ResponseEntity<Object> addDocumentType(@Validated @RequestBody AddDocumentTypeDTO addDocumentTypeDTO,
                                                  BindingResult bindingResult) {
        try {
            log.info("Add addDocumentTypeDTO request received by : {}", addDocumentTypeDTO);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = documentTypeService.addDocumentType(addDocumentTypeDTO);
                if (msg.equals(MessageConstant.DOCUMENT_TYPE_ADDED)) {
                    log.info(MessageConstant.DOCUMENT_TYPE_ADDED);
                    return responseHandler.response("", MessageConstant.DOCUMENT_TYPE_ADDED, true, HttpStatus.OK);
                }
                return responseHandler.response("", MessageConstant.DOCUMENT_TYPE_ALREADY_EXIST, false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_ADDED_DOCUMENT_TYPE, e);
        }
        log.info(MessageConstant.ERROR_WHILE_ADDED_DOCUMENT_TYPE);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_ADDED_DOCUMENT_TYPE, false,
                HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = UrlConstants.DELETE_DOCUMENT_TYPE)
    public ResponseEntity<Object> deleteDocumentType(@RequestParam(value = "documentTypeId") Long documentTypeId) {
        try {

            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = documentTypeService.deleteDocumentType(documentTypeId);
                if (msg.equals(MessageConstant.DOCUMENT_TYPE_DELETED)) {
                    log.info(MessageConstant.DOCUMENT_TYPE_DELETED);
                    return responseHandler.response("", MessageConstant.DOCUMENT_TYPE_DELETED, true, HttpStatus.OK);
                }
                return responseHandler.response("", MessageConstant.DATA_NOT_PRESENT_WITH_YOUR_REQUEST_ID, false,
                        HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_DELETED_DOCUMENT_TYPE, e);
        }
        log.info(MessageConstant.ERROR_WHILE_DELETED_DOCUMENT_TYPE);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_DELETED_DOCUMENT_TYPE, false,
                HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = UrlConstants.UPDATE_DOCUMENT_TYPE)
    public ResponseEntity<Object> updateDocumentType(
            @Validated @RequestBody UpdateDocumentTypeDTO updateDocumentTypeDTO, BindingResult bindingResult) {
        try {
            log.info("update document type request received by : {}", updateDocumentTypeDTO);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = documentTypeService.updateDocumentType(updateDocumentTypeDTO);
                if (msg.equalsIgnoreCase(MessageConstant.DOCUMENT_TYPE_UPDATED)) {
                    log.info(MessageConstant.DOCUMENT_TYPE_UPDATED);
                    return responseHandler.response("", MessageConstant.DOCUMENT_TYPE_UPDATED, true, HttpStatus.OK);
                } else if (msg.equalsIgnoreCase(MessageConstant.DOCUMENT_TYPE_ALREADY_EXIST)) {
                    return responseHandler.response("", MessageConstant.DOCUMENT_TYPE_ALREADY_EXIST, false,
                            HttpStatus.OK);
                }
                return responseHandler.response("", MessageConstant.ERROR_WHILE_UPDATED_DOCUMENT_TYPE, false,
                        HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_UPDATED_DOCUMENT_TYPE, e);
        }
        log.info(MessageConstant.ERROR_WHILE_UPDATED_DOCUMENT_TYPE);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_UPDATED_DOCUMENT_TYPE, false,
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = UrlConstants.GET_DOC_TYPE_AND_DOC_NAME)
    ResponseEntity<Object> getDocumentTypeAndDocumentNameList() {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                List<DocumentTypeNameAndIdRespDTO> getAllDocumentTypeAndDocName = documentTypeService
                        .getDocumentTypeAndDocumentNameList();
                if (!getAllDocumentTypeAndDocName.isEmpty()) {
                    log.info(MessageConstant.ALL_DOCUMENT_TYPE);
                    return responseHandler.response(getAllDocumentTypeAndDocName, MessageConstant.ALL_DOCUMENT_TYPE,
                            true, HttpStatus.OK);
                }
                log.info(MessageConstant.DATA_NOT_FOUND);
                return responseHandler.response("", MessageConstant.DOCUMENT_TYPE_NOT_FOUND, false, HttpStatus.OK);
            }
            return isValidOrNot;

        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_GET_DOCUMENT_TYPE, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_DOCUMENT_TYPE, false,
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = UrlConstants.GET_DOCUMENT_TYPE_BY_ID)
    public ResponseEntity<Object> getDocumentTypeById(@RequestParam(name = "id") Long id) {
        try {
            log.info("Get document type by id request received by : {}", id);
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                DocumentTypeRespDTO documentType = documentTypeService.getDocumentTypeById(id);
                if (documentType != null) {
                    log.info(MessageConstant.DOCUMENT_TYPE_FETCHED_SUCCESS);
                    return responseHandler.response(documentType, MessageConstant.DOCUMENT_TYPE_FETCHED_SUCCESS, true,
                            HttpStatus.OK);
                } else {
                    log.info(MessageConstant.NO_DATA_FOUND);
                    return responseHandler.response("", MessageConstant.NO_DATA_FOUND, false, HttpStatus.OK);
                }
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_FETCHING_DOCUMENT_TYPE_BY_ID, e);
        }
        log.info(MessageConstant.ERROR_WHILE_FETCHING_DOCUMENT_TYPE_BY_ID);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_FETCHING_DOCUMENT_TYPE_BY_ID, false,
                HttpStatus.BAD_REQUEST);
    }


    @GetMapping(UrlConstants.GET_DEPARTMENT_BY_DOCUMENT_TYPE)
    public ResponseEntity<Object> getDepartmentByDocumentName(@RequestParam String documentName) {
        try {
            log.info("Get department by document name request received by : {}", documentName);
            Set<DocumentTypeMappingWithDepartment> departments = documentTypeService.getDepartmentByDocumentName(documentName);
            if (!departments.isEmpty()) {
                log.info(MessageConstant.DEPARTMENT_FETCHED_SUCCESS);
                return responseHandler.response(departments, MessageConstant.DEPARTMENT_FETCHED_SUCCESS, true, HttpStatus.OK);
            }
            log.info(MessageConstant.NO_DATA_FOUND);
            return responseHandler.response("", MessageConstant.NO_DATA_FOUND, false, HttpStatus.OK);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_FETCHING_DEPARTMENT_BY_DOCUMENT_NAME, e);
        }
        log.info(MessageConstant.ERROR_WHILE_FETCHING_DEPARTMENT_BY_DOCUMENT_NAME);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_FETCHING_DEPARTMENT_BY_DOCUMENT_NAME, false,
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping(UrlConstants.GET_SUPPLY_VERTICAL_BY_DOCUMENT_TYPE)
    public ResponseEntity<Object> getSupplyVerticalByDocumentName(@RequestParam String documentName) {
        try {
            log.info("Get supply verticalB by document name request received by : {}", documentName);
            Set<DocumentTypeMappingWithSupplyVertical> supplyVerticals = documentTypeService.getSupplyVerticalByDocumentName(documentName);
            if (!supplyVerticals.isEmpty()) {
                log.info(MessageConstant.ALL_SUPPLY_VERTICAL_FETCHED_SUCCESS);
                return responseHandler.response(supplyVerticals, MessageConstant.ALL_SUPPLY_VERTICAL_FETCHED_SUCCESS, true, HttpStatus.OK);
            }
            log.info(MessageConstant.NO_DATA_FOUND);
            return responseHandler.response("", MessageConstant.NO_DATA_FOUND, false, HttpStatus.OK);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_FETCHING_ALL_SUPPLY_VERTICALS, e);
        }
        log.info(MessageConstant.ERROR_WHILE_FETCHING_ALL_SUPPLY_VERTICALS);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_FETCHING_ALL_SUPPLY_VERTICALS, false,
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping(UrlConstants.GET_DIVISION_BY_DOCUMENT_TYPE)
    public ResponseEntity<Object> getDivisionByDocumentName(@RequestParam String documentName) {
        try {
            log.info("Get division by document name request received by : {}", documentName);
            Set<DocumentTypeMappingWithDivision> divisions = documentTypeService.getDivisionByDocumentName(documentName);
            if (!divisions.isEmpty()) {
                log.info(MessageConstant.ALL_DIVISION_FETCH_SUCCESS);
                return responseHandler.response(divisions, MessageConstant.ALL_DIVISION_FETCH_SUCCESS, true, HttpStatus.OK);
            }
            log.info(MessageConstant.NO_DATA_FOUND);
            return responseHandler.response("", MessageConstant.NO_DATA_FOUND, false, HttpStatus.OK);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_FETCHING_DIVISIONS, e);
        }
        log.info(MessageConstant.ERROR_FETCHING_DIVISIONS);
        return responseHandler.response("", MessageConstant.ERROR_FETCHING_DIVISIONS, false,
                HttpStatus.BAD_REQUEST);
    }
}
