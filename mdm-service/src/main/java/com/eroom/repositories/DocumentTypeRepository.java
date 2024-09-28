package com.eroom.repositories;

import com.eroom.dtos.DocumentTypeNameAndIdRespDTO;
import com.eroom.dtos.DocumentTypeRespDTO;
import com.eroom.entities.DocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {

    Optional<DocumentType> findByIdAndStatus(Long documentTypeId, String status);

    Optional<DocumentTypeRespDTO> findRespByIdAndStatus(Long documentTypeId, String status);

    List<DocumentTypeNameAndIdRespDTO> findByStatus(String status);

    Page<DocumentTypeRespDTO> findByStatusOrderByIdDesc(Pageable pageable, String status);

    Optional<DocumentType> findByDocumentNameIgnoreCaseAndStatus(String documentName, String activeStatus);

    Optional<DocumentType> findByDocumentNameAndStatus(String documentName, String status);

}