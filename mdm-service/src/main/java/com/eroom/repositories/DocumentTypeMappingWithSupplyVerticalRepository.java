package com.eroom.repositories;

import com.eroom.entities.DocumentTypeMappingWithSupplyVertical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeMappingWithSupplyVerticalRepository extends JpaRepository<DocumentTypeMappingWithSupplyVertical, Long> {

    List<DocumentTypeMappingWithSupplyVertical> findAllByDocumentTypeId(Long documentTypeId);

}