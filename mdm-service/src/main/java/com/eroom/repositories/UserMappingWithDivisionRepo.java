package com.eroom.repositories;

import com.eroom.entities.UserMappingWithDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMappingWithDivisionRepo extends JpaRepository<UserMappingWithDivision, Long> {


}