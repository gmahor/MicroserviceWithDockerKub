package com.eroom.repositories;

import com.eroom.dtos.GeographicalTerritoryRespDTO;
import com.eroom.entities.GeographicalTerritory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GeographicalTerritoryRepository extends JpaRepository<GeographicalTerritory, Long> {

    @Query(nativeQuery = true, value = "select distinct gt.geography_desc  from geographical_territory gt order by gt.geography_desc asc")
    Page<GeographicalTerritoryRespDTO> getAllGeographicalTerritoryWithPagination(Pageable pageable);

    @Query(nativeQuery = true, value = "select distinct gt.geography_desc  from geographical_territory gt order by gt.geography_desc asc")
    Set<GeographicalTerritoryRespDTO> getAllGeographicalTerritory();
}