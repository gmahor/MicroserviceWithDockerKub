package com.eroom.repositories;

import com.eroom.dtos.SupplyVerticalMappedWithDocAssignmentRespDTO;
import com.eroom.entities.DocumentAssignmentMappingWithSupplyVertical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentAssignmentMappingWithSupplyVerticalRepository extends JpaRepository<DocumentAssignmentMappingWithSupplyVertical, Long> {
    List<DocumentAssignmentMappingWithSupplyVertical> findAllByDocumentAssignmentId(long documentAssignmentId);

    List<SupplyVerticalMappedWithDocAssignmentRespDTO> findByDocumentAssignmentId(long documentAssignmentId);
}
