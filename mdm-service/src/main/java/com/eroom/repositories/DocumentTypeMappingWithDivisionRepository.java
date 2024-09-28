package com.eroom.repositories;

import com.eroom.entities.DocumentTypeMappingWithDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeMappingWithDivisionRepository extends JpaRepository<DocumentTypeMappingWithDivision, Long> {

    List<DocumentTypeMappingWithDivision> findAllByDocumentTypeId(Long documentTypeId);
}