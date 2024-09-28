package com.eroom.repositories;

import com.eroom.dtos.CheckerUserMappedWithDocAssignmentRespDTO;
import com.eroom.entities.CheckerMappingWithDocAssigment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckerMapDocAssignRepo extends JpaRepository<CheckerMappingWithDocAssigment, Long> {

    List<CheckerUserMappedWithDocAssignmentRespDTO> findAllByDocumentAssignmentId(Long documentAssignmentId);


}