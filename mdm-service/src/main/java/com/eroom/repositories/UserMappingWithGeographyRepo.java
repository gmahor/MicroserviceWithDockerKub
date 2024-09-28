package com.eroom.repositories;

import com.eroom.entities.UserMappingWithGeography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMappingWithGeographyRepo extends JpaRepository<UserMappingWithGeography, Long> {


}