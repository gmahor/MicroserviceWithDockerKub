package com.eroom.repositories;

import com.eroom.entities.DocumentAssignmentMappingWithDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentAssignmentMappingWithDepartmentRepository extends JpaRepository<DocumentAssignmentMappingWithDepartment, Long> {

}
