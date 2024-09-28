package com.eroom.repositories;

import com.eroom.entities.DocumentTypeMappingWithDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeMappingWithDepartmentRepository extends JpaRepository<DocumentTypeMappingWithDepartment, Long> {

    List<DocumentTypeMappingWithDepartment> findAllByDocumentTypeId(Long documentTypeId);
}
