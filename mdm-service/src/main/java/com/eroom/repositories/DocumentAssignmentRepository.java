package com.eroom.repositories;

import com.eroom.dtos.DocumentAssignmentRespDTO;
import com.eroom.entities.DocumentAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentAssignmentRepository extends JpaRepository<DocumentAssignment, Long> {

    Optional<DocumentAssignmentRespDTO> findRespByIdAndStatus(Long documentAssignId, String status);

    Optional<DocumentAssignment> findByIdAndStatus(Long documentAssignId, String status);

    Optional<DocumentAssignment> findByDocumentNameAndStatus(String documentName, String activeStatus);

    Page<DocumentAssignmentRespDTO> findAllByStatusOrderByIdDesc(Pageable pageable, String status);

    boolean existsByDocumentNameAndStatus(String documentName, String status);

}