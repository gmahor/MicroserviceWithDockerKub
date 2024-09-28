package com.eroom.repositories;

import com.eroom.dtos.FinalApproverUserMappedWithDocAssignmentRespDTO;
import com.eroom.entities.FinalApproverMappingWithDocAssigment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApproverMapDocAssignRepo extends JpaRepository<FinalApproverMappingWithDocAssigment, Long> {

    List<FinalApproverUserMappedWithDocAssignmentRespDTO> findAllByDocumentAssignmentId(Long documentAssignmentId);


}