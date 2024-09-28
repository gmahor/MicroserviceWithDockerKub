package com.eroom.controllers;

import com.eroom.constants.MessageConstant;
import com.eroom.constants.UrlConstants;
import com.eroom.dtos.AddDocumentAssignmentDTO;
import com.eroom.dtos.DocumentAssignmentRespDTO;
import com.eroom.dtos.PageDTO;
import com.eroom.dtos.UpdateDocumentAssignmentDTO;
import com.eroom.services.DocumentAssignmentService;
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
@RequestMapping(path = UrlConstants.BASE_API_V1_DOCUMENT_ASSIGNMENT)
public class DocumentAssignmentController {

    private final CommonUtil commonUtil;

    private final ResponseHandler responseHandler;

    private final DocumentAssignmentService documentAssignmentService;

    @Autowired
    public DocumentAssignmentController(CommonUtil commonUtil, DocumentAssignmentService documentAssignmentService,
                                        ResponseHandler responseHandler) {
        this.commonUtil = commonUtil;
        this.responseHandler = responseHandler;
        this.documentAssignmentService = documentAssignmentService;
    }

    @GetMapping(path = UrlConstants.GET_ALL_DOC_ASSIGNED)
    ResponseEntity<Object> getAllDocumentAssignment(@RequestParam(defaultValue = "0") Integer pageNo,
                                                    @RequestParam(defaultValue = "50") Integer size) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {

                PageDTO getAllDocumentAssigned = documentAssignmentService.getAllDocumentAssignment(pageNo, size);
                if (getAllDocumentAssigned.getTotalRecords() > 0) {
                    log.info(MessageConstant.ALL_DOCUMENT_ASSIGNMENT);
                    return responseHandler.response(getAllDocumentAssigned, MessageConstant.ALL_DOCUMENT_ASSIGNMENT,
                            true, HttpStatus.OK);
                }
                log.info(MessageConstant.DOCUMENT_ASSIGNMENT_NOT_FOUND);
                return responseHandler.response("", MessageConstant.DOCUMENT_ASSIGNMENT_NOT_FOUND, false,
                        HttpStatus.OK);

            }
            return isValidOrNot;

        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_GET_DOCUMENT_ASSIGNMENT, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_DOCUMENT_ASSIGNMENT, false,
                HttpStatus.BAD_REQUEST);

    }

    @PostMapping(path = UrlConstants.ADD_DOCUMENT_ASSIGNMENT)
    public ResponseEntity<Object> addDocumentAssigmnet(
            @Validated @RequestBody AddDocumentAssignmentDTO addDocumentAssignmentDTO, BindingResult bindingResult) {
        try {
            log.info("Add addDocumentAssignmentDTO request received by : {}", addDocumentAssignmentDTO);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = documentAssignmentService.addDocumentAssigmnet(addDocumentAssignmentDTO);
                if (msg.equals(MessageConstant.DOCUMENT_ASSIGNMENT_ADDED)) {
                    log.info(MessageConstant.DOCUMENT_ASSIGNMENT_ADDED);
                    return responseHandler.response("", MessageConstant.DOCUMENT_ASSIGNMENT_ADDED, true, HttpStatus.OK);
                }
                return responseHandler.response("", MessageConstant.DOCUMENT_TYPE_ALREADY_EXIST, false, HttpStatus.OK);

            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_ADDED_DOCUMENT_ASSIGNED, e);
        }
        log.info(MessageConstant.ERROR_WHILE_ADDED_DOCUMENT_ASSIGNED);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_ADDED_DOCUMENT_ASSIGNED, false,
                HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = UrlConstants.DELETE_DOCUMENT_ASSIGNED)
    public ResponseEntity<Object> deleteDocumentAssigned(
            @RequestParam(value = "documentAssignId") Long documentAssignId) {
        try {

            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = documentAssignmentService.deleteDocumentAssigned(documentAssignId);
                if (msg.equals(MessageConstant.DOCUMENT_ASSIGNED_DELETED)) {
                    log.info(MessageConstant.DOCUMENT_ASSIGNED_DELETED);
                    return responseHandler.response("", MessageConstant.DOCUMENT_ASSIGNED_DELETED, true, HttpStatus.OK);
                }
                return responseHandler.response("", MessageConstant.DATA_NOT_PRESENT_WITH_YOUR_REQUEST_ID, false,
                        HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_DELETED_DOCUMENT_ASSIGNED, e);
        }
        log.info(MessageConstant.ERROR_WHILE_DELETED_DOCUMENT_ASSIGNED);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_DELETED_DOCUMENT_ASSIGNED, false,
                HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = UrlConstants.UPDATE_DOCUMENT_ASSIGNMENT)
    public ResponseEntity<Object> updateDocumentAssigned(
            @Validated @RequestBody UpdateDocumentAssignmentDTO updateDocumentAssignmentDTO,
            BindingResult bindingResult) {
        try {
            log.info(" updateDocumentAssignmentDTO request received by : {}", updateDocumentAssignmentDTO);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                String msg = documentAssignmentService.updateDocumentAssigned(updateDocumentAssignmentDTO);
                if (msg.equals(MessageConstant.DOCUMENT_ASSIGNED_UPDATED)) {
                    log.info(MessageConstant.DOCUMENT_ASSIGNED_UPDATED);
                    return responseHandler.response("", MessageConstant.DOCUMENT_ASSIGNED_UPDATED, true, HttpStatus.OK);
                } else if (msg.equalsIgnoreCase(MessageConstant.DOCUMENT_TYPE_ALREADY_EXIST)) {
                    return responseHandler.response("", MessageConstant.DOCUMENT_TYPE_ALREADY_EXIST, false,
                            HttpStatus.OK);
                }
                return responseHandler.response("", MessageConstant.DOC_ASSIGNED_DETAILS_NOT_FOUND_WITH_REQUEST_ID,
                        false, HttpStatus.OK);
            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_WHILE_UPDATED_DOCUMENT_ASSIGNED, e);
        }
        log.info(MessageConstant.ERROR_WHILE_UPDATED_DOCUMENT_ASSIGNED);
        return responseHandler.response("", MessageConstant.ERROR_WHILE_UPDATED_DOCUMENT_ASSIGNED, false,
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = UrlConstants.GET_DOCUMENT_ASSIGNMENT_BY_ID)
    public ResponseEntity<Object> getDocumentAssignmentById(@RequestParam(value = "documentId") Long documentId) {
        try {
            ResponseEntity<Object> isValidOrNot = commonUtil.checkUserIsValidOrNot();
            if (isValidOrNot == null) {
                DocumentAssignmentRespDTO documentAssignment = documentAssignmentService.getDocumentAssignmentById(documentId);
                if (documentAssignment != null) {
                    log.info(MessageConstant.GET_DOCUMENT_ASSIGNED_DETAILS);
                    return responseHandler.response(documentAssignment,
                            MessageConstant.GET_DOCUMENT_ASSIGNED_DETAILS, true, HttpStatus.OK);
                }
                log.info(MessageConstant.DATA_NOT_FOUND);
                return responseHandler.response("", MessageConstant.DATA_NOT_FOUND, false, HttpStatus.OK);

            }
            return isValidOrNot;
        } catch (Exception e) {
            log.error(e.toString());
        }
        return responseHandler.response("", MessageConstant.ERROR_WHILE_GET_DOCUMENT_ASSIGNMENT_DETAILS, false,
                HttpStatus.BAD_REQUEST);

    }

}
