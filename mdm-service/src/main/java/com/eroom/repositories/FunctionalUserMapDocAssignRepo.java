package com.eroom.repositories;

import com.eroom.dtos.FuncUserMappedWithDocAssignmentRespDTO;
import com.eroom.entities.FunctionalUserMappingWithDocAssigment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionalUserMapDocAssignRepo extends JpaRepository<FunctionalUserMappingWithDocAssigment, Long> {

    List<FuncUserMappedWithDocAssignmentRespDTO> findAllByDocumentAssignmentId(Long documentAssignmentId);


}