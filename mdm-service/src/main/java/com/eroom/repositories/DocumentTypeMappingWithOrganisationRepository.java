package com.eroom.repositories;

import com.eroom.entities.DocumentTypeMappingWithOrganisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeMappingWithOrganisationRepository
        extends JpaRepository<DocumentTypeMappingWithOrganisation, Long> {

    List<DocumentTypeMappingWithOrganisation> findAllByDocumentTypeId(Long documentTypeId);

}