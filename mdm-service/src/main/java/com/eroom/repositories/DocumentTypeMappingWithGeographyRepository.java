package com.eroom.repositories;

import com.eroom.entities.DocumentTypeMappingWithGeography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeMappingWithGeographyRepository extends JpaRepository<DocumentTypeMappingWithGeography, Long> {

    List<DocumentTypeMappingWithGeography> findAllByDocumentTypeId(Long documentTypeId);
}
