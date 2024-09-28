package com.eroom.services.impl;

import com.eroom.constants.GenericConstants;
import com.eroom.constants.MessageConstant;
import com.eroom.dtos.*;
import com.eroom.entities.*;
import com.eroom.repositories.*;
import com.eroom.services.DocumentAssignmentService;
import com.eroom.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class DocumentAssignmentServiceImpl implements DocumentAssignmentService {

    private final CommonUtil commonUtil;

    private final DocumentAssignmentRepository documentAssignmentRepository;

    private final CheckerMapDocAssignRepo checkerMapDocAssignRepo;

    private final ApproverMapDocAssignRepo approverMapDocAssignRepo;

    private final ProjectManagerMapDocAssignRepo projectManagerMapDocAssignRepo;

    private final FunctionalUserMapDocAssignRepo functionalUserMapDocAssignRepo;

    private final DocumentAssignmentMappingWithDivisionRepository documentAssignmentMappingWithDivisionRepository;

    private final DocumentAssignmentMappingWithSupplyVerticalRepository documentAssignmentMappingWithSupplyVerticalRepository;

    private final DocumentAssignmentMappingWithDepartmentRepository documentAssignmentMappingWithDepartmentRepository;


    @Override
    public String addDocumentAssigmnet(AddDocumentAssignmentDTO addDocumentAssignmentDTO) {
        Optional<DocumentAssignment> documentAssignmentOptional = documentAssignmentRepository
                .findByDocumentNameAndStatus(addDocumentAssignmentDTO.getDocumentName(),
                        GenericConstants.ACTIVE_STATUS);
        if (documentAssignmentOptional.isEmpty()) {
            DocumentAssignment documentAssignment = new DocumentAssignment();
            documentAssignment.setDocumentTypeId(addDocumentAssignmentDTO.getDocumentTypeId());
            documentAssignment.setDocumentName(addDocumentAssignmentDTO.getDocumentName());
            setAndSaveAllMappingData(addDocumentAssignmentDTO, documentAssignment);
            return MessageConstant.DOCUMENT_ASSIGNMENT_ADDED;
        }
        return MessageConstant.DOCUMENT_TYPE_ALREADY_EXIST;
    }


    public void setAndSaveAllMappingData(AddDocumentAssignmentDTO addDocumentAssignmentDTO, DocumentAssignment documentAssignment) {
        CompletableFuture<Set<DocumentAssignmentMappingWithSupplyVertical>> mappedSupplyVerticalDataFuture = mappedSupplyVerticalWithDocumentAssignment(addDocumentAssignmentDTO.getSupplyVerticalList());
        CompletableFuture<Set<DocumentAssignmentMappingWithDivision>> mappedDivisionDataFuture = mappedDivisionWithDocumentAssignment(addDocumentAssignmentDTO.getDivisionList());
        CompletableFuture<Set<CheckerMappingWithDocAssigment>> mappedCheckerUserDataFuture = checkerMappingWithDocAssignment(addDocumentAssignmentDTO.getCheckerMapDTOList());
        CompletableFuture<Set<FinalApproverMappingWithDocAssigment>> mappedFinalUserDataFuture = finalApproverMappingWithDocAssigment(addDocumentAssignmentDTO.getApproverMapDTOList());
        CompletableFuture<Set<FunctionalUserMappingWithDocAssigment>> mappedFuncUserDataFuture = functionalUserMappingWithDocAssigment(addDocumentAssignmentDTO.getFunctionalUserMapDTOList());
        CompletableFuture<Set<ProjectManagerMappingWithDocAssigment>> mappedProjectManagerUserDataFuture = projectManagerMappingWithDocAssigment(addDocumentAssignmentDTO.getProjectManagerMapDTOList());
        CompletableFuture<Set<DocumentAssignmentMappingWithDepartment>> mappedDepartmentsDataFuture = departmentMappingWithDocAssigment(addDocumentAssignmentDTO.getDepartmentList());
        documentAssignment.setCreatedBy(commonUtil.loggedInUserName());
        documentAssignment.setModifiedBy(commonUtil.loggedInUserName());
        documentAssignmentRepository.save(documentAssignment);
        CompletableFuture.allOf(
                mappedSupplyVerticalDataFuture, mappedDivisionDataFuture, mappedCheckerUserDataFuture,
                mappedFinalUserDataFuture, mappedFuncUserDataFuture, mappedProjectManagerUserDataFuture,
                mappedDepartmentsDataFuture
        ).thenRun(() -> {
            try {
                Set<DocumentAssignmentMappingWithSupplyVertical> mappedSupplyVerticalData = mappedSupplyVerticalDataFuture.get();
                if (!mappedSupplyVerticalData.isEmpty()) {
                    documentAssignment.setDocumentAssignmentMappingWithSupplyVerticals(mappedSupplyVerticalData);
                }

                Set<DocumentAssignmentMappingWithDivision> mappedDivisionData = mappedDivisionDataFuture.get();
                if (!mappedDivisionData.isEmpty()) {
                    documentAssignment.setDocumentAssignmentMappingWithDivisions(mappedDivisionData);
                }

                Set<CheckerMappingWithDocAssigment> mappedCheckerUserData = mappedCheckerUserDataFuture.get();
                if (!mappedDivisionData.isEmpty()) {
                    documentAssignment.setCheckerMappingWithDocAssignment(mappedCheckerUserData);
                }

                Set<FinalApproverMappingWithDocAssigment> mappedFinalUserData = mappedFinalUserDataFuture.get();
                if (!mappedFinalUserData.isEmpty()) {
                    documentAssignment.setFinalApproverMappingWithDocAssigment(mappedFinalUserData);
                }

                Set<FunctionalUserMappingWithDocAssigment> mappedFuncUserData = mappedFuncUserDataFuture.get();
                if (!mappedFuncUserData.isEmpty()) {
                    documentAssignment.setFunctionalUserMappingWithDocAssigment(mappedFuncUserData);
                }

                Set<ProjectManagerMappingWithDocAssigment> mappedProjectManagerUserData = mappedProjectManagerUserDataFuture.get();
                if (!mappedProjectManagerUserData.isEmpty()) {
                    documentAssignment.setProjectManagerMappingWithDocAssigment(mappedProjectManagerUserData);
                }

                Set<DocumentAssignmentMappingWithDepartment> mappedDepartmentData = mappedDepartmentsDataFuture.get();
                if (!mappedDepartmentData.isEmpty()) {
                    documentAssignment.setDepartmentMappingWithDocAssigment(mappedDepartmentData);
                }

                documentAssignmentRepository.save(documentAssignment);
            } catch (InterruptedException e) {
                log.error("Interrupted exception while setAndSaveAllMappingData method - ", e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }

    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<DocumentAssignmentMappingWithSupplyVertical>> mappedSupplyVerticalWithDocumentAssignment(
            List<SupplyVerticalDTO> supplyVerticalList) {
        Set<DocumentAssignmentMappingWithSupplyVertical> mappedSupplyVerticalData = supplyVerticalList.stream().map(supplyVerticalDTO -> {
            DocumentAssignmentMappingWithSupplyVertical documentAssignmentMappingWithSupplyVertical = new DocumentAssignmentMappingWithSupplyVertical();
            documentAssignmentMappingWithSupplyVertical.setSupplyVertical(supplyVerticalDTO.getSupplyVertical());
            return documentAssignmentMappingWithSupplyVertical;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappedSupplyVerticalData);
    }


    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<DocumentAssignmentMappingWithDivision>> mappedDivisionWithDocumentAssignment(
            List<DivisionDTO> divisionList) {
        Set<DocumentAssignmentMappingWithDivision> mappingWithDivisionSet = divisionList.stream().map(divisionDTO -> {
            DocumentAssignmentMappingWithDivision documentAssignmentMappingWithDivision = new DocumentAssignmentMappingWithDivision();
            documentAssignmentMappingWithDivision.setDivision(divisionDTO.getDivision());
            documentAssignmentMappingWithDivision.setDivisionCode(divisionDTO.getDivisionCode());
            return documentAssignmentMappingWithDivision;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappingWithDivisionSet);
    }


    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<FunctionalUserMappingWithDocAssigment>> functionalUserMappingWithDocAssigment(
            List<FunctionalUserMapDTO> functionalUserMapDTOList) {
        Set<FunctionalUserMappingWithDocAssigment> mappingWithDocAssigmentSet = functionalUserMapDTOList.stream().map(functionalUserMapDto -> {
            FunctionalUserMappingWithDocAssigment functionalUserMappingWithDocAssigment = new FunctionalUserMappingWithDocAssigment();
            functionalUserMappingWithDocAssigment.setFunctionalId(functionalUserMapDto.getFunctionalUserId());
            functionalUserMappingWithDocAssigment.setFunctionalUser(functionalUserMapDto.getFuncationUser());
            return functionalUserMappingWithDocAssigment;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappingWithDocAssigmentSet);
    }


    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<ProjectManagerMappingWithDocAssigment>> projectManagerMappingWithDocAssigment(
            List<ProjectManagerMapDTO> projectManagerMapDTOList) {
        Set<ProjectManagerMappingWithDocAssigment> mappingWithDocAssigmentSet = projectManagerMapDTOList.stream().map(projectManagerMapDto -> {
            ProjectManagerMappingWithDocAssigment projectManagerMappingWithDocAssigment = new ProjectManagerMappingWithDocAssigment();
            projectManagerMappingWithDocAssigment.setProjectManager(projectManagerMapDto.getProjectManager());
            projectManagerMappingWithDocAssigment.setProjectManagerId(projectManagerMapDto.getProjectManagerId());
            return projectManagerMappingWithDocAssigment;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappingWithDocAssigmentSet);
    }


    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<FinalApproverMappingWithDocAssigment>> finalApproverMappingWithDocAssigment(
            List<ApproverMapDTO> approverMapDTOsList) {
        Set<FinalApproverMappingWithDocAssigment> mappingWithDocAssigmentSet = approverMapDTOsList.stream().map(approverMapDTOList -> {
            FinalApproverMappingWithDocAssigment finalApproverMappingWithDocAssigment = new FinalApproverMappingWithDocAssigment();
            finalApproverMappingWithDocAssigment.setFinalApprover(approverMapDTOList.getApprover());
            finalApproverMappingWithDocAssigment.setFinalApproverId(approverMapDTOList.getApproverId());
            return finalApproverMappingWithDocAssigment;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappingWithDocAssigmentSet);
    }


    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<CheckerMappingWithDocAssigment>> checkerMappingWithDocAssignment(
            List<CheckerMapDTO> checkerMapDTOsList) {
        Set<CheckerMappingWithDocAssigment> mappingWithDocAssigmentSet = checkerMapDTOsList.stream().map(checkerMapDTOList -> {
            CheckerMappingWithDocAssigment checkerMappingWithDocAssigment = new CheckerMappingWithDocAssigment();
            checkerMappingWithDocAssigment.setCheckerId(checkerMapDTOList.getCheckerId());
            checkerMappingWithDocAssigment.setChecker(checkerMapDTOList.getChecker());
            return checkerMappingWithDocAssigment;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappingWithDocAssigmentSet);
    }

    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<DocumentAssignmentMappingWithDepartment>> departmentMappingWithDocAssigment(List<DepartmentDTO> departmentList) {
        Set<DocumentAssignmentMappingWithDepartment> mappingWithDocAssignment = departmentList.stream().map(departmentDTO -> {
            DocumentAssignmentMappingWithDepartment documentAssignmentMappingWithDepartment = new DocumentAssignmentMappingWithDepartment();
            documentAssignmentMappingWithDepartment.setDepartmentCode(departmentDTO.getDepartmentCode());
            documentAssignmentMappingWithDepartment.setDepartmentDesc(departmentDTO.getDepartmentDesc());
            return documentAssignmentMappingWithDepartment;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappingWithDocAssignment);
    }

    @Override
    public String deleteDocumentAssigned(Long documentAssignId) {
        Optional<DocumentAssignment> documentAssignmentOptional = documentAssignmentRepository
                .findByIdAndStatus(documentAssignId, GenericConstants.STATUS);
        if (documentAssignmentOptional.isPresent()) {
            DocumentAssignment documentAssign = documentAssignmentOptional.get();
            documentAssign.setStatus(GenericConstants.IN_ACTIVE_STATUS);
            deleteMappedSupplyVertical(documentAssign.getDocumentAssignmentMappingWithSupplyVerticals());
            deleteMappedDivision(documentAssign.getDocumentAssignmentMappingWithDivisions());
            inActiveFunctionalUser(documentAssign.getFunctionalUserMappingWithDocAssigment());
            inActiveProjectManager(documentAssign.getProjectManagerMappingWithDocAssigment());
            inActiveChecker(documentAssign.getCheckerMappingWithDocAssignment());
            inActiveApprover(documentAssign.getFinalApproverMappingWithDocAssigment());
            inActiveDepartments(documentAssign.getDepartmentMappingWithDocAssigment());
            documentAssignmentRepository.save(documentAssign);
            return MessageConstant.DOCUMENT_ASSIGNED_DELETED;
        }
        return MessageConstant.DATA_NOT_PRESENT_WITH_YOUR_REQUEST_ID;
    }

    private void deleteMappedSupplyVertical(
            Set<DocumentAssignmentMappingWithSupplyVertical> documentAssignmentMappingWithSupplyVerticals) {
        CompletableFuture.runAsync(() -> documentAssignmentMappingWithSupplyVerticals.forEach(documentAssignmentMappingWithSupplyVertical -> {
            documentAssignmentMappingWithSupplyVertical.setDocumentAssignmentId(null);
            documentAssignmentMappingWithSupplyVerticalRepository.save(documentAssignmentMappingWithSupplyVertical);
            log.info(MessageConstant.DELETE_MAPPED_SUPPLY_VERTICAL_DATA_BY_DOCUMENT_ASSIGNMENT,
                    documentAssignmentMappingWithSupplyVertical.getDocumentAssignmentId());
        }));
    }

    private void deleteMappedDivision(
            Set<DocumentAssignmentMappingWithDivision> documentAssignmentMappingWithDivisions) {
        CompletableFuture.runAsync(() -> documentAssignmentMappingWithDivisions.forEach(documentAssignmentMappingWithDivision -> {
            documentAssignmentMappingWithDivision.setDocumentAssignmentId(null);
            documentAssignmentMappingWithDivisionRepository.save(documentAssignmentMappingWithDivision);
            log.info(MessageConstant.DELETE_MAPPED_DIVISION_DATA_BY_DOCUMENT_ASSIGNMENT,
                    documentAssignmentMappingWithDivision.getDocumentAssignmentId());
        }));
    }

    private void inActiveApprover(Set<FinalApproverMappingWithDocAssigment> finalApproverMappingWithDocAssigment) {
        CompletableFuture.runAsync(() -> {
            Set<FinalApproverMappingWithDocAssigment> finalApprover = finalApproverMappingWithDocAssigment.stream()
                    .peek(finalApproverMap -> finalApproverMap.setDocumentAssignmentId(null)).collect(Collectors.toSet());
            approverMapDocAssignRepo.saveAll(finalApprover);
        });
    }

    private void inActiveChecker(Set<CheckerMappingWithDocAssigment> checkerMappingWithDocAssignment) {
        CompletableFuture.runAsync(() -> {
            Set<CheckerMappingWithDocAssigment> checkerMappingWithDoc = checkerMappingWithDocAssignment.stream()
                    .peek(checkerMapping -> checkerMapping.setDocumentAssignmentId(null)).collect(Collectors.toSet());
            checkerMapDocAssignRepo.saveAll(checkerMappingWithDoc);
        });

    }

    private void inActiveProjectManager(
            Set<ProjectManagerMappingWithDocAssigment> projectManagerMappingWithDocAssigment) {
        CompletableFuture.runAsync(() -> {
            Set<ProjectManagerMappingWithDocAssigment> projectManagerMapping = projectManagerMappingWithDocAssigment
                    .stream().peek(projectManager -> projectManager.setDocumentAssignmentId(null)).collect(Collectors.toSet());
            projectManagerMapDocAssignRepo.saveAll(projectManagerMapping);
        });
    }

    private void inActiveFunctionalUser(
            Set<FunctionalUserMappingWithDocAssigment> functionalUserMappingWithDocAssigment) {
        CompletableFuture.runAsync(() -> {
            Set<FunctionalUserMappingWithDocAssigment> functionalMap = functionalUserMappingWithDocAssigment.stream()
                    .peek(functionalUserMapping -> functionalUserMapping.setDocumentAssignmentId(null)).collect(Collectors.toSet());
            functionalUserMapDocAssignRepo.saveAll(functionalMap);
        });
    }

    private void inActiveDepartments(Set<DocumentAssignmentMappingWithDepartment> departmentMappingWithDocAssigment) {
        CompletableFuture.runAsync(() -> {
            Set<DocumentAssignmentMappingWithDepartment> departmentMap = departmentMappingWithDocAssigment
                    .stream()
                    .peek(departmentMapping -> departmentMapping.setDocumentAssignmentId(null)).collect(Collectors.toSet());
            documentAssignmentMappingWithDepartmentRepository.saveAll(departmentMap);
        });
    }

    @Override
    public String updateDocumentAssigned(UpdateDocumentAssignmentDTO updateDocumentAssignmentDTO) {
        Optional<DocumentAssignment> documentAssignedOptional = documentAssignmentRepository
                .findByIdAndStatus(updateDocumentAssignmentDTO.getId(), GenericConstants.STATUS);
        if (documentAssignedOptional.isPresent()) {
            DocumentAssignment oldDocumentAssignment = documentAssignedOptional.get();
            if (updateDocumentAssignmentDTO.getDivisionList() != null && !updateDocumentAssignmentDTO.getDivisionList().isEmpty()) {
                CompletableFuture<Set<DocumentAssignmentMappingWithDivision>> updatedMappedDivisionData = updateMappedDivision(updateDocumentAssignmentDTO.getDivisionList(), documentAssignedOptional.get().getId());
                Set<DocumentAssignmentMappingWithDivision> mappingWithDivisionSet = updatedMappedDivisionData.join();
                if (!mappingWithDivisionSet.isEmpty()) {
                    oldDocumentAssignment.setDocumentAssignmentMappingWithDivisions(mappingWithDivisionSet);
                }
            }

            if (updateDocumentAssignmentDTO.getSupplyVerticalList() != null && !updateDocumentAssignmentDTO.getSupplyVerticalList().isEmpty()) {
                CompletableFuture<Set<DocumentAssignmentMappingWithSupplyVertical>> updatedMappedSupplyVerticalData = updateMappedSupplyVertical(updateDocumentAssignmentDTO.getSupplyVerticalList(), documentAssignedOptional.get().getId());
                Set<DocumentAssignmentMappingWithSupplyVertical> mappingWithSupplyVerticalSet = updatedMappedSupplyVerticalData.join();
                if (!mappingWithSupplyVerticalSet.isEmpty()) {
                    oldDocumentAssignment.setDocumentAssignmentMappingWithSupplyVerticals(mappingWithSupplyVerticalSet);
                }
            }

            if (updateDocumentAssignmentDTO.getFunctionalUserMapDTOList() != null && !updateDocumentAssignmentDTO.getFunctionalUserMapDTOList().isEmpty()) {
                CompletableFuture<Set<FunctionalUserMappingWithDocAssigment>> updatedFunctionalUsersData = functionalUserMappingWithDocAssigment(updateDocumentAssignmentDTO.getFunctionalUserMapDTOList());
                Set<FunctionalUserMappingWithDocAssigment> functionalUserMappingWithDocAssigmentSet = updatedFunctionalUsersData.join();
                if (!functionalUserMappingWithDocAssigmentSet.isEmpty()) {
                    oldDocumentAssignment.setFunctionalUserMappingWithDocAssigment(functionalUserMappingWithDocAssigmentSet);
                }
            }

            if (updateDocumentAssignmentDTO.getCheckerMapDTOList() != null && !updateDocumentAssignmentDTO.getCheckerMapDTOList().isEmpty()) {
                CompletableFuture<Set<CheckerMappingWithDocAssigment>> updatedCheckerUsersData = checkerMappingWithDocAssignment(updateDocumentAssignmentDTO.getCheckerMapDTOList());
                Set<CheckerMappingWithDocAssigment> checkerMappingWithDocAssigmentSet = updatedCheckerUsersData.join();
                if (!checkerMappingWithDocAssigmentSet.isEmpty()) {
                    oldDocumentAssignment.setCheckerMappingWithDocAssignment(checkerMappingWithDocAssigmentSet);
                }
            }

            if (updateDocumentAssignmentDTO.getApproverMapDTOList() != null && !updateDocumentAssignmentDTO.getApproverMapDTOList().isEmpty()) {
                CompletableFuture<Set<FinalApproverMappingWithDocAssigment>> updatedFinalApproverUsersData = finalApproverMappingWithDocAssigment(updateDocumentAssignmentDTO.getApproverMapDTOList());
                Set<FinalApproverMappingWithDocAssigment> approverMappingWithDocAssigmentSet = updatedFinalApproverUsersData.join();
                if (!approverMappingWithDocAssigmentSet.isEmpty()) {
                    oldDocumentAssignment.setFinalApproverMappingWithDocAssigment(approverMappingWithDocAssigmentSet);
                }
            }

            if (updateDocumentAssignmentDTO.getProjectManagerMapDTOList() != null && !updateDocumentAssignmentDTO.getProjectManagerMapDTOList().isEmpty()) {
                CompletableFuture<Set<ProjectManagerMappingWithDocAssigment>> updatedProjectManagerUsersData = projectManagerMappingWithDocAssigment(updateDocumentAssignmentDTO.getProjectManagerMapDTOList());
                Set<ProjectManagerMappingWithDocAssigment> projectManagerMappingWithDocAssigmentSet = updatedProjectManagerUsersData.join();
                if (!projectManagerMappingWithDocAssigmentSet.isEmpty()) {
                    oldDocumentAssignment.setProjectManagerMappingWithDocAssigment(projectManagerMappingWithDocAssigmentSet);
                }
            }

            if (updateDocumentAssignmentDTO.getDepartmentList() != null && !updateDocumentAssignmentDTO.getDepartmentList().isEmpty()) {
                CompletableFuture<Set<DocumentAssignmentMappingWithDepartment>> updatedDepartmentsData = departmentMappingWithDocAssigment(updateDocumentAssignmentDTO.getDepartmentList());
                Set<DocumentAssignmentMappingWithDepartment> departmentMappingWithDocAssigmentSet = updatedDepartmentsData.join();
                if (!departmentMappingWithDocAssigmentSet.isEmpty()) {
                    oldDocumentAssignment.setDepartmentMappingWithDocAssigment(departmentMappingWithDocAssigmentSet);
                }
            }
            documentAssignmentRepository.save(oldDocumentAssignment);
            return MessageConstant.DOCUMENT_ASSIGNED_UPDATED;
        }
        return MessageConstant.DOC_ASSIGNED_DETAILS_NOT_FOUND_WITH_REQUEST_ID;
    }

    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<DocumentAssignmentMappingWithDivision>> updateMappedDivision(List<DivisionDTO> divisionList,
                                                                                              long documentAssignmentId) {
        List<DocumentAssignmentMappingWithDivision> documentAssignmentMappingWithDivisions = documentAssignmentMappingWithDivisionRepository
                .findAllByDocumentAssignmentId(documentAssignmentId);
        documentAssignmentMappingWithDivisions.forEach(documentAssignmentMappingWithDivision -> {
            documentAssignmentMappingWithDivision.setDocumentAssignmentId(null);
            documentAssignmentMappingWithDivisionRepository.save(documentAssignmentMappingWithDivision);
        });
        return mappedDivisionWithDocumentAssignment(divisionList);
    }

    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<DocumentAssignmentMappingWithSupplyVertical>> updateMappedSupplyVertical(
            List<SupplyVerticalDTO> supplyVerticalList, long documentAssignmentId) {
        documentAssignmentMappingWithSupplyVerticalRepository
                .findAllByDocumentAssignmentId(documentAssignmentId)
                .forEach(documentAssignmentMappingWithSupplyVertical -> {
                    documentAssignmentMappingWithSupplyVertical.setDocumentAssignmentId(null);
                    documentAssignmentMappingWithSupplyVerticalRepository
                            .save(documentAssignmentMappingWithSupplyVertical);
                });
        return mappedSupplyVerticalWithDocumentAssignment(supplyVerticalList);
    }


    @Override
    public PageDTO getAllDocumentAssignment(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<DocumentAssignmentRespDTO> allDocumentAssignment = documentAssignmentRepository
                .findAllByStatusOrderByIdDesc(pageable, GenericConstants.STATUS);
        return commonUtil.getDetailsPage(allDocumentAssignment.getContent(), allDocumentAssignment.getContent().size(),
                allDocumentAssignment.getTotalPages(), allDocumentAssignment.getTotalElements());
    }

    @Override
    public DocumentAssignmentRespDTO getDocumentAssignmentById(Long documentId) {
        return documentAssignmentRepository
                .findRespByIdAndStatus(documentId, GenericConstants.STATUS).orElse(null);
    }

}
