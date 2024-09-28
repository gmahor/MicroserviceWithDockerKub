package com.eroom.repositories;

import com.eroom.entities.Division;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {

    Page<Division> findAllByStatusOrderByDescription(Pageable pageable, String status);

}
