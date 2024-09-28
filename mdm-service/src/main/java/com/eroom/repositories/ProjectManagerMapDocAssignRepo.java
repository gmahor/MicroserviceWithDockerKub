package com.eroom.repositories;

import com.eroom.dtos.ProjectUserMappedWithDocAssignmentRespDTO;
import com.eroom.entities.ProjectManagerMappingWithDocAssigment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectManagerMapDocAssignRepo extends JpaRepository<ProjectManagerMappingWithDocAssigment, Long> {

    List<ProjectUserMappedWithDocAssignmentRespDTO> findAllByDocumentAssignmentId(Long documentAssignmentId);


}