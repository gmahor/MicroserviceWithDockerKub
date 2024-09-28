package com.eroom.repositories;

import com.eroom.entities.DocumentAssignmentMappingWithDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentAssignmentMappingWithDivisionRepository extends JpaRepository<DocumentAssignmentMappingWithDivision, Long> {

    List<DocumentAssignmentMappingWithDivision> findAllByDocumentAssignmentId(long documentAssignmentId);

}
