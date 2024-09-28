package com.eroom.repositories;

import com.eroom.entities.SupplyVertical;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplyVerticalRepository extends JpaRepository<SupplyVertical, Long> {

    Optional<SupplyVertical> findByIdAndStatus(Long id, String status);

    Page<SupplyVertical> findAllByStatusOrderByIdDesc(Pageable pageable, String status);

    Page<SupplyVertical> findAllBySupplyVerticalIgnoreCaseAndStatusOrderByIdDesc(Pageable pageable,
                                                                                 String supplyVertical, String status);


}