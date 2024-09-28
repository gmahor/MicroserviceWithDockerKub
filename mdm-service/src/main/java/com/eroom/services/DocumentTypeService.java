package com.eroom.services;

import com.eroom.dtos.*;
import com.eroom.entities.DocumentTypeMappingWithDepartment;
import com.eroom.entities.DocumentTypeMappingWithDivision;
import com.eroom.entities.DocumentTypeMappingWithSupplyVertical;

import java.util.List;
import java.util.Set;

public interface DocumentTypeService {

    PageDTO getDocumentType(Integer pageNo, Integer size);

    String addDocumentType(AddDocumentTypeDTO addDocumentTypeDTO);

    String deleteDocumentType(Long documentTypeId);

    String updateDocumentType(UpdateDocumentTypeDTO updateDocumentTypeDTO);

    List<DocumentTypeNameAndIdRespDTO> getDocumentTypeAndDocumentNameList();

    DocumentTypeRespDTO getDocumentTypeById(Long id);

    Set<DocumentTypeMappingWithDepartment> getDepartmentByDocumentName(String documentName);

    Set<DocumentTypeMappingWithSupplyVertical> getSupplyVerticalByDocumentName(String documentName);

    Set<DocumentTypeMappingWithDivision> getDivisionByDocumentName(String documentName);
}
