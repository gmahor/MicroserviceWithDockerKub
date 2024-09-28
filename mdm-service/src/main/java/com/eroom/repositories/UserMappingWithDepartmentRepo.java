package com.eroom.repositories;

import com.eroom.entities.UserMappingWithDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMappingWithDepartmentRepo extends JpaRepository<UserMappingWithDepartment, Long> {


}