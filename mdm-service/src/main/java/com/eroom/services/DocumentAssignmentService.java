package com.eroom.services;

import com.eroom.dtos.AddDocumentAssignmentDTO;
import com.eroom.dtos.DocumentAssignmentRespDTO;
import com.eroom.dtos.PageDTO;
import com.eroom.dtos.UpdateDocumentAssignmentDTO;

public interface DocumentAssignmentService {

    PageDTO getAllDocumentAssignment(Integer pageNo, Integer size);

    String addDocumentAssigmnet(AddDocumentAssignmentDTO addDocumentAssignmentDTO);

    String deleteDocumentAssigned(Long documentAssignId);

    String updateDocumentAssigned(UpdateDocumentAssignmentDTO updateDocumentAssignmentDTO);

    DocumentAssignmentRespDTO getDocumentAssignmentById(Long documentId);

}
