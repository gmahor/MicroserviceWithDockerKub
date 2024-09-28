package com.eroom.services.impl;

import com.eroom.constants.GenericConstants;
import com.eroom.constants.MessageConstant;
import com.eroom.dtos.*;
import com.eroom.entities.*;
import com.eroom.repositories.*;
import com.eroom.services.DocumentTypeService;
import com.eroom.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final CommonUtil commonUtil;

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentTypeMappingWithDivisionRepository documentTypeMappingWithDivisionRepository;

    private final DocumentTypeMappingWithSupplyVerticalRepository documentTypeMappingWithSupplyVerticalRepository;

    private final DocumentTypeMappingWithOrganisationRepository documentTypeMappingWithOrganisationRepository;

    private final DocumentTypeMappingWithDepartmentRepository documentTypeMappingWithDepartmentRepository;

    private final DocumentAssignmentRepository documentAssignmentRepository;

    private final DocumentTypeMappingWithGeographyRepository documentTypeMappingWithGeographyRepository;

    @Override
    public PageDTO getDocumentType(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<DocumentTypeRespDTO> allDocumentTypeData = documentTypeRepository.findByStatusOrderByIdDesc(pageable,
                GenericConstants.STATUS);
        return commonUtil.getDetailsPage(allDocumentTypeData.getContent(), allDocumentTypeData.getContent().size(),
                allDocumentTypeData.getTotalPages(), allDocumentTypeData.getTotalElements());
    }

    @Override
    public String addDocumentType(AddDocumentTypeDTO addDocumentTypeDTO) {
        Optional<DocumentType> checkDocument = documentTypeRepository.findByDocumentNameIgnoreCaseAndStatus(
                addDocumentTypeDTO.getDocumentName(), GenericConstants.ACTIVE_STATUS);
        if (checkDocument.isEmpty()) {
            DocumentType documentType = new DocumentType();
            documentType.setDocumentAbbreviation(addDocumentTypeDTO.getDocumentAbbreviation());
            documentType.setDocumentName(addDocumentTypeDTO.getDocumentName());
            documentType.setDocumentType(addDocumentTypeDTO.getDocumentType());
            documentType.setLevel(addDocumentTypeDTO.getLevel());
            documentType.setCreatedBy(commonUtil.loggedInUserName());
            documentType.setModifiedBy(commonUtil.loggedInUserName());
            documentTypeRepository.save(documentType);
            setAndSavedDocumentTypeMappedData(addDocumentTypeDTO, documentType);
            return MessageConstant.DOCUMENT_TYPE_ADDED;
        }
        return MessageConstant.DOCUMENT_TYPE_ALREADY_EXIST;
    }

    private void setAndSavedDocumentTypeMappedData(AddDocumentTypeDTO addDocumentTypeDTO, DocumentType documentType) {
        CompletableFuture<Set<DocumentTypeMappingWithSupplyVertical>> supplyVerticalFuture = mappedSupplyVerticalWithDocumentTypeAsync(addDocumentTypeDTO);
        CompletableFuture<Set<DocumentTypeMappingWithOrganisation>> organisationFuture = documentTypeMappingWithOrganisationAsync(addDocumentTypeDTO);
        CompletableFuture<Set<DocumentTypeMappingWithDivision>> divisionFuture = mappedDivisionWithDocumentTypeAsync(addDocumentTypeDTO);
        CompletableFuture<Set<DocumentTypeMappingWithDepartment>> departmentsFuture = mappedDepartmentsWithDocumentType(addDocumentTypeDTO);
        CompletableFuture<Set<DocumentTypeMappingWithGeography>> geographyFuture = mappedGeographyWithDocumentTypeAsync(addDocumentTypeDTO);

        CompletableFuture.allOf(supplyVerticalFuture, organisationFuture,
                divisionFuture, departmentsFuture, geographyFuture
        ).thenRun(() -> {
            try {
                Set<DocumentTypeMappingWithSupplyVertical> documentTypeMappingWithSupplyVerticals = supplyVerticalFuture.get();
                if (!documentTypeMappingWithSupplyVerticals.isEmpty()) {
                    documentType.setDocumentTypeMappingWithSupplyVerticals(documentTypeMappingWithSupplyVerticals);
                }

                Set<DocumentTypeMappingWithOrganisation> documentTypeMappingWithOrganisations = organisationFuture.get();
                if (!documentTypeMappingWithOrganisations.isEmpty()) {
                    documentType.setDocumentTypeMappingWithOrganisation(documentTypeMappingWithOrganisations);
                }

                Set<DocumentTypeMappingWithDivision> documentTypeMappingWithDivisions = divisionFuture.get();
                if (!documentTypeMappingWithDivisions.isEmpty()) {
                    documentType.setDocumentTypeMappingWithDivision(documentTypeMappingWithDivisions);
                }

                Set<DocumentTypeMappingWithDepartment> documentTypeMappingWithDepartments = departmentsFuture.get();
                if (!documentTypeMappingWithDepartments.isEmpty()) {
                    documentType.setDocumentTypeMappingWithDepartment(documentTypeMappingWithDepartments);
                }

                Set<DocumentTypeMappingWithGeography> documentTypeMappingWithGeographies = geographyFuture.get();
                if (!documentTypeMappingWithGeographies.isEmpty()) {
                    documentType.setDocumentTypeMappingWithGeographies(documentTypeMappingWithGeographies);
                }
                documentTypeRepository.save(documentType);
            } catch (InterruptedException e) {
                log.error("Interrupted exception occur in setAndSavedDocumentTypeMappedData method - ", e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                log.error(MessageConstant.ERROR_WHILE_ADDED_DOCUMENT_TYPE, e);
            }
        });
    }


    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<DocumentTypeMappingWithSupplyVertical>> mappedSupplyVerticalWithDocumentTypeAsync(
            AddDocumentTypeDTO addDocumentTypeDTO) {
        Set<DocumentTypeMappingWithSupplyVertical> mappingWithDocumentType = addDocumentTypeDTO.getSupplyVerticalList().stream().map(supplyVerticalDTO -> {
            DocumentTypeMappingWithSupplyVertical documentTypeMappingWithSupplyVertical = new DocumentTypeMappingWithSupplyVertical();
            documentTypeMappingWithSupplyVertical.setSupplyVertical(supplyVerticalDTO.getSupplyVertical());
            return documentTypeMappingWithSupplyVertical;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappingWithDocumentType);
    }

    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<DocumentTypeMappingWithOrganisation>> documentTypeMappingWithOrganisationAsync(
            AddDocumentTypeDTO addDocumentTypeDTO) {
        Set<DocumentTypeMappingWithOrganisation> mappingWithOrganisations = addDocumentTypeDTO.getOrganisationDtoList().stream().map(organisationDto -> {
            DocumentTypeMappingWithOrganisation documentTypeMappingWithOrganisation = new DocumentTypeMappingWithOrganisation();
            documentTypeMappingWithOrganisation.setOrgId(organisationDto.getOrgId());
            documentTypeMappingWithOrganisation.setOrgName(organisationDto.getOrgName());
            return documentTypeMappingWithOrganisation;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappingWithOrganisations);
    }

    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<DocumentTypeMappingWithDivision>> mappedDivisionWithDocumentTypeAsync(
            AddDocumentTypeDTO addDocumentTypeDTO) {
        Set<DocumentTypeMappingWithDivision> mappingWithDivisions = addDocumentTypeDTO.getDivisionList().stream().map(divisionDTO -> {
            DocumentTypeMappingWithDivision documentTypeMappingWithDivision = new DocumentTypeMappingWithDivision();
            documentTypeMappingWithDivision.setDivision(divisionDTO.getDivision());
            documentTypeMappingWithDivision.setDivisionCode(divisionDTO.getDivisionCode());
            return documentTypeMappingWithDivision;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappingWithDivisions);
    }

    public CompletableFuture<Set<DocumentTypeMappingWithDepartment>> mappedDepartmentsWithDocumentType(AddDocumentTypeDTO addDocumentTypeDTO) {
        Set<DocumentTypeMappingWithDepartment> mappedWithDepartment = addDocumentTypeDTO
                .getDepartmentList()
                .stream()
                .map(departmentDTO -> {
                    DocumentTypeMappingWithDepartment documentTypeMappingWithDepartment = new DocumentTypeMappingWithDepartment();
                    documentTypeMappingWithDepartment.setDepartmentCode(departmentDTO.getDepartmentCode());
                    documentTypeMappingWithDepartment.setDepartmentDesc(departmentDTO.getDepartmentDesc());
                    return documentTypeMappingWithDepartment;
                }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappedWithDepartment);
    }

    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<DocumentTypeMappingWithGeography>> mappedGeographyWithDocumentTypeAsync(
            AddDocumentTypeDTO addDocumentTypeDTO) {
        Set<DocumentTypeMappingWithGeography> mappingWithDocumentType = addDocumentTypeDTO.getGeographyList().stream().map(geographyDTO -> {
            DocumentTypeMappingWithGeography documentTypeMappingWithGeography = new DocumentTypeMappingWithGeography();
            documentTypeMappingWithGeography.setGeography(geographyDTO.getGeography());
            return documentTypeMappingWithGeography;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappingWithDocumentType);
    }

    @Override
    public String deleteDocumentType(Long documentTypeId) {
        Optional<DocumentType> documentTypeOptional = documentTypeRepository.findByIdAndStatus(documentTypeId,
                GenericConstants.STATUS);
        if (documentTypeOptional.isPresent()) {
            DocumentType documentType = documentTypeOptional.get();
            deleteDocumentTypeAsync(documentType);
            return MessageConstant.DOCUMENT_TYPE_DELETED;
        }
        return MessageConstant.DATA_NOT_PRESENT_WITH_YOUR_REQUEST_ID;
    }

    public void deleteDocumentTypeAsync(DocumentType documentType) {
        documentType.setStatus(GenericConstants.IN_ACTIVE_STATUS);
        documentTypeRepository.save(documentType);
        deletingMappedDivisions(documentType);
        deletingMappedSupplyVertical(documentType);
        deletingMappedOrganisations(documentType);
        deletingMappedDepartments(documentType);
    }


    @Override
    public String updateDocumentType(UpdateDocumentTypeDTO updateDocumentTypeDTO) {
        Optional<DocumentType> documentTypeOptional = documentTypeRepository.findById(updateDocumentTypeDTO.getId());
        if (documentTypeOptional.isPresent()) {
            Optional<DocumentType> checkDocument = documentTypeRepository.findByDocumentNameIgnoreCaseAndStatus(
                    updateDocumentTypeDTO.getDocumentName(), GenericConstants.ACTIVE_STATUS);
            if (checkDocument.isEmpty()) {
                return setUpdateDocumentTypeDetails(updateDocumentTypeDTO, documentTypeOptional.get());
            } else {
                return MessageConstant.DOCUMENT_TYPE_ALREADY_EXIST;
            }
        }
        return MessageConstant.ERROR_WHILE_UPDATED_DOCUMENT_TYPE;
    }

    private String setUpdateDocumentTypeDetails(UpdateDocumentTypeDTO updateDocumentTypeDTO,
                                                DocumentType documentType) {
        documentType.setDocumentName(
                updateDocumentTypeDTO.getDocumentName() != null ? updateDocumentTypeDTO.getDocumentName()
                        : documentType.getDocumentName());
        documentType.setDocumentType(
                updateDocumentTypeDTO.getDocumentType() != null ? updateDocumentTypeDTO.getDocumentType()
                        : documentType.getDocumentType());
        documentType.setDocumentAbbreviation(updateDocumentTypeDTO.getDocumentAbbreviation() != null
                ? updateDocumentTypeDTO.getDocumentAbbreviation()
                : documentType.getDocumentAbbreviation());
        documentType.setLevel(
                updateDocumentTypeDTO.getLevel() != null ? updateDocumentTypeDTO.getLevel() : documentType.getLevel());
        documentType.setModifiedBy(commonUtil.loggedInUserName());
        documentTypeRepository.save(documentType);

        // Updating supply vertical
        updateMappedSupplyVertical(updateDocumentTypeDTO.getSupplyVerticalList(), documentType);

        // Updating Division
        updateMappedDivision(updateDocumentTypeDTO.getDivisionList(), documentType);

        // Updating Organisation
        updateMappedOrganisation(updateDocumentTypeDTO.getOrganisationDtoList(), documentType);

        // Updating Department
        updateMappedDepartments(updateDocumentTypeDTO.getDepartmentList(), documentType);

        // Updating Geographies
        updateMappedGeographies(updateDocumentTypeDTO.getGeographyList(), documentType);

        documentTypeRepository.save(documentType);

        return MessageConstant.DOCUMENT_TYPE_UPDATED;

    }

    @Async(value = "mdmAsyncExecutor")
    public void updateMappedOrganisation(List<OrganisationDTO> organisationDtoList, DocumentType documentType) {
        try {
            if (organisationDtoList != null && !organisationDtoList.isEmpty()) {
                // New organisation data added
                Set<DocumentTypeMappingWithOrganisation> documentTypeMappingWithOrganisations = organisationDtoList
                        .stream()
                        .map(organisationDto -> {
                            DocumentTypeMappingWithOrganisation docTypeMappingWithOrg = new DocumentTypeMappingWithOrganisation();
                            docTypeMappingWithOrg.setOrgId(organisationDto.getOrgId());
                            docTypeMappingWithOrg.setOrgName(organisationDto.getOrgName());
                            return docTypeMappingWithOrg;
                        }).collect(Collectors.toSet());
                CompletableFuture<Set<DocumentTypeMappingWithOrganisation>> isProcessingCompleted = CompletableFuture
                        .completedFuture(documentTypeMappingWithOrganisations);
                if (isProcessingCompleted.isDone()) {
                    // Deleting old organisation data
                    deletingMappedOrganisations(documentType);
                    documentType.setDocumentTypeMappingWithOrganisation(isProcessingCompleted.get());
                }
            }
        } catch (InterruptedException e) {
            log.error("Interrupted exception while updating document type organisation - ", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error while updating document type organisation - ", e);
        }
    }

    @Async(value = "mdmAsyncExecutor")
    public void deletingMappedOrganisations(DocumentType documentType) {
        CompletableFuture.runAsync(() -> {
            try {
                Set<DocumentTypeMappingWithOrganisation> documentOrganisations = documentTypeMappingWithOrganisationRepository
                        .findAllByDocumentTypeId(documentType.getId())
                        .stream()
                        .peek(documentTypeMappingWithOrganisation -> documentTypeMappingWithOrganisation.setDocumentTypeId(null)).collect(Collectors.toSet());
                documentTypeMappingWithOrganisationRepository.saveAll(documentOrganisations);
            } catch (Exception e) {
                log.error("Error in processing organisation mapping - ", e);
            }
        });
    }

    @Async(value = "mdmAsyncExecutor")
    public void updateMappedSupplyVertical(List<SupplyVerticalDTO> supplyVerticalList, DocumentType documentType) {
        try {
            if (supplyVerticalList != null && !supplyVerticalList.isEmpty()) {
                // New supply vertical data added
                Set<DocumentTypeMappingWithSupplyVertical> documentTypeMappingWithSupplyVerticals = supplyVerticalList.stream().map(supplyVerticalDTO -> {
                    DocumentTypeMappingWithSupplyVertical documentTypeMappingWithSupplyVertical = new DocumentTypeMappingWithSupplyVertical();
                    documentTypeMappingWithSupplyVertical.setSupplyVertical(supplyVerticalDTO.getSupplyVertical());
                    return documentTypeMappingWithSupplyVertical;
                }).collect(Collectors.toSet());
                CompletableFuture<Set<DocumentTypeMappingWithSupplyVertical>> isProcessingCompleted = CompletableFuture
                        .completedFuture(documentTypeMappingWithSupplyVerticals);
                if (isProcessingCompleted.isDone()) {
                    // Deleting old supply vertical data
                    deletingMappedSupplyVertical(documentType);
                    documentType.setDocumentTypeMappingWithSupplyVerticals(isProcessingCompleted.get());
                }
            }
        } catch (InterruptedException e) {
            log.error("Interrupted exception while updating document type supply vertical - ", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error while updating document type supply vertical - ", e);
        }
    }

    @Async(value = "mdmAsyncExecutor")
    public void deletingMappedSupplyVertical(DocumentType documentType) {
        CompletableFuture.runAsync(() -> {
            try {
                Set<DocumentTypeMappingWithSupplyVertical> documentSupplyVerticals = documentTypeMappingWithSupplyVerticalRepository
                        .findAllByDocumentTypeId(documentType.getId())
                        .stream()
                        .peek(documentTypeMappingWithSupplyVertical -> documentTypeMappingWithSupplyVertical.setDocumentTypeId(null)).collect(Collectors.toSet());
                documentTypeMappingWithSupplyVerticalRepository.saveAll(documentSupplyVerticals);
            } catch (Exception e) {
                log.error("Error in processing supply vertical mapping asynchronously - ", e);
            }
        });
    }

    @Async(value = "mdmAsyncExecutor")
    public void updateMappedDivision(List<DivisionDTO> divisionList, DocumentType documentType) {
        try {
            if (divisionList != null && !divisionList.isEmpty()) {
                // New divisions  data added
                Set<DocumentTypeMappingWithDivision> documentTypeMappingWithDivisions = divisionList.stream().map(divisionDTO -> {
                    DocumentTypeMappingWithDivision documentTypeMappingWithDivision = new DocumentTypeMappingWithDivision();
                    documentTypeMappingWithDivision.setDivision(divisionDTO.getDivision());
                    documentTypeMappingWithDivision.setDivisionCode(divisionDTO.getDivisionCode());
                    return documentTypeMappingWithDivision;
                }).collect(Collectors.toSet());
                CompletableFuture<Set<DocumentTypeMappingWithDivision>> isProcessingCompleted = CompletableFuture
                        .completedFuture(documentTypeMappingWithDivisions);
                if (isProcessingCompleted.isDone()) {
                    // Delete old division data
                    deletingMappedDivisions(documentType);
                    documentType.setDocumentTypeMappingWithDivision(isProcessingCompleted.get());
                }
            }
        } catch (InterruptedException e) {
            log.error("Interrupted exception while updating document type division - ", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error while updating document type division - ", e);
        }
    }

    @Async(value = "mdmAsyncExecutor")
    public void deletingMappedDivisions(DocumentType documentType) {
        CompletableFuture.runAsync(() -> {
            try {
                Set<DocumentTypeMappingWithDivision> documentDivisions = documentTypeMappingWithDivisionRepository
                        .findAllByDocumentTypeId(documentType.getId())
                        .stream()
                        .peek(documentTypeMappingWithDivision -> documentTypeMappingWithDivision.setDocumentTypeId(null)).collect(Collectors.toSet());
                documentTypeMappingWithDivisionRepository.saveAll(documentDivisions);
            } catch (Exception e) {
                log.error("Error in processing divisions mapping asynchronously - ", e);
            }
        });
    }

    @Async(value = "mdmAsyncExecutor")
    public void updateMappedDepartments(List<DepartmentDTO> departmentList, DocumentType documentType) {
        try {
            if (departmentList != null && !departmentList.isEmpty()) {

                // New department  data added
                Set<DocumentTypeMappingWithDepartment> documentTypeMappingWithDepartments = departmentList.stream().map(departmentDTO -> {
                    DocumentTypeMappingWithDepartment documentTypeMappingWithDepartment = new DocumentTypeMappingWithDepartment();
                    documentTypeMappingWithDepartment.setDepartmentCode(departmentDTO.getDepartmentCode());
                    documentTypeMappingWithDepartment.setDepartmentDesc(departmentDTO.getDepartmentDesc());
                    return documentTypeMappingWithDepartment;
                }).collect(Collectors.toSet());

                CompletableFuture<Set<DocumentTypeMappingWithDepartment>> isProcessingCompleted = CompletableFuture
                        .completedFuture(documentTypeMappingWithDepartments);
                if (isProcessingCompleted.isDone()) {
                    // Delete old department data
                    deletingMappedDepartments(documentType);
                    documentType.setDocumentTypeMappingWithDepartment(isProcessingCompleted.get());
                }
            }
        } catch (InterruptedException e) {
            log.error("Interrupted exception while document type department - ", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error while updating document type department - ", e);
        }
    }

    @Async(value = "mdmAsyncExecutor")
    public void deletingMappedDepartments(DocumentType documentType) {
        CompletableFuture.runAsync(() -> {
            try {
                Set<DocumentTypeMappingWithDepartment> documentDepartments = documentTypeMappingWithDepartmentRepository
                        .findAllByDocumentTypeId(documentType.getId())
                        .stream()
                        .peek(documentTypeMappingWithDepartment -> documentTypeMappingWithDepartment.setDocumentTypeId(null)).collect(Collectors.toSet());
                documentTypeMappingWithDepartmentRepository.saveAll(documentDepartments);
            } catch (Exception e) {
                log.error("Error in processing department mapping asynchronously", e);
            }
        });
    }


    @Async(value = "mdmAsyncExecutor")
    public void updateMappedGeographies(List<GeographyDTO> geographyList, DocumentType documentType) {
        try {
            if (geographyList != null && !geographyList.isEmpty()) {

                // New geographies  data added
                Set<DocumentTypeMappingWithGeography> documentTypeMappingWithGeographies = geographyList.stream().map(geographyDTO -> {
                    DocumentTypeMappingWithGeography documentTypeMappingWithGeography = new DocumentTypeMappingWithGeography();
                    documentTypeMappingWithGeography.setGeography(geographyDTO.getGeography());
                    return documentTypeMappingWithGeography;
                }).collect(Collectors.toSet());

                CompletableFuture<Set<DocumentTypeMappingWithGeography>> isProcessingCompleted = CompletableFuture
                        .completedFuture(documentTypeMappingWithGeographies);
                if (isProcessingCompleted.isDone()) {
                    // Delete old geographies data
                    deletingMappedGeography(documentType);
                    documentType.setDocumentTypeMappingWithGeographies(isProcessingCompleted.get());
                }
            }
        } catch (InterruptedException e) {
            log.error("Interrupted exception while document type geographies - ", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error while updating document type geographies - ", e);
        }
    }


    @Async(value = "mdmAsyncExecutor")
    public void deletingMappedGeography(DocumentType documentType) {
        CompletableFuture.runAsync(() -> {
            try {
                Set<DocumentTypeMappingWithGeography> documentDepartments = documentTypeMappingWithGeographyRepository
                        .findAllByDocumentTypeId(documentType.getId())
                        .stream()
                        .peek(documentTypeMappingWithDepartment -> documentTypeMappingWithDepartment.setDocumentTypeId(null)).collect(Collectors.toSet());
                documentTypeMappingWithGeographyRepository.saveAll(documentDepartments);
            } catch (Exception e) {
                log.error("Error in processing geographies mapping asynchronously", e);
            }
        });
    }

    @Override
    public List<DocumentTypeNameAndIdRespDTO> getDocumentTypeAndDocumentNameList() {
        return documentTypeRepository.findByStatus(GenericConstants.STATUS).stream()
                .filter(documentTypeName -> !documentAssignmentRepository.existsByDocumentNameAndStatus(documentTypeName.getDocumentName(), GenericConstants.ACTIVE_STATUS))
                .collect(Collectors.toList());
    }

    @Override
    public DocumentTypeRespDTO getDocumentTypeById(Long id) {
        return documentTypeRepository
                .findRespByIdAndStatus(id, GenericConstants.STATUS)
                .orElse(null);
    }

    @Override
    public Set<DocumentTypeMappingWithDepartment> getDepartmentByDocumentName(String documentName) {
        Optional<DocumentType> optionalDocumentType = documentTypeRepository.findByDocumentNameAndStatus(documentName, GenericConstants.STATUS);
        if (optionalDocumentType.isPresent()) {
            return optionalDocumentType.get().getDocumentTypeMappingWithDepartment();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<DocumentTypeMappingWithSupplyVertical> getSupplyVerticalByDocumentName(String documentName) {
        Optional<DocumentType> optionalDocumentType = documentTypeRepository.findByDocumentNameAndStatus(documentName, GenericConstants.STATUS);
        if (optionalDocumentType.isPresent()) {
            return optionalDocumentType.get().getDocumentTypeMappingWithSupplyVerticals();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<DocumentTypeMappingWithDivision> getDivisionByDocumentName(String documentName) {
        Optional<DocumentType> optionalDocumentType = documentTypeRepository.findByDocumentNameAndStatus(documentName, GenericConstants.STATUS);
        if (optionalDocumentType.isPresent()) {
            return optionalDocumentType.get().getDocumentTypeMappingWithDivision();
        }
        return Collections.emptySet();
    }
}
