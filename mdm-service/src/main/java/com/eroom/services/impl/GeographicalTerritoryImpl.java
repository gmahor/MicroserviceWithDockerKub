package com.eroom.services.impl;

import com.eroom.dtos.GeographicalTerritoryDTO;
import com.eroom.dtos.GeographicalTerritoryRespDTO;
import com.eroom.dtos.PageDTO;
import com.eroom.repositories.GeographicalTerritoryRepository;
import com.eroom.services.GeographicalTerritoryService;
import com.eroom.utils.CommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GeographicalTerritoryImpl implements GeographicalTerritoryService {

    private final GeographicalTerritoryRepository geographicalTerritoryRepository;

    private final CommonUtil commonUtil;

    @Override
    public PageDTO getAllGeographicalTerritory(Integer pageNo, Integer size) {
        if (pageNo != null && size != null) {
            Pageable pageable = PageRequest.of(pageNo, size);
            Page<GeographicalTerritoryRespDTO> allGeographicalTerritories = geographicalTerritoryRepository.getAllGeographicalTerritoryWithPagination(pageable);
            Set<GeographicalTerritoryDTO> geographicalTerritories = allGeographicalTerritories
                    .stream().map(geographicalTerritoryRespDTO -> {
                        GeographicalTerritoryDTO geographicalTerritoryDTO = new GeographicalTerritoryDTO();
                        geographicalTerritoryDTO.setGeographyDesc(geographicalTerritoryRespDTO.getGeography_desc());
                        return geographicalTerritoryDTO;
                    })
                    .collect(Collectors.toSet());
            return commonUtil.getDetailsPage(geographicalTerritories, geographicalTerritories.size(),
                    allGeographicalTerritories.getTotalPages(), allGeographicalTerritories.getTotalElements());
        } else {
            Set<GeographicalTerritoryRespDTO> allGeographicalTerritories = geographicalTerritoryRepository.getAllGeographicalTerritory();
            Set<GeographicalTerritoryDTO> geographicalTerritories = allGeographicalTerritories
                    .stream().map(geographicalTerritoryRespDTO -> {
                        GeographicalTerritoryDTO geographicalTerritoryDTO = new GeographicalTerritoryDTO();
                        geographicalTerritoryDTO.setGeographyDesc(geographicalTerritoryRespDTO.getGeography_desc());
                        return geographicalTerritoryDTO;
                    }).collect(Collectors.toSet());
            return commonUtil.getDetailsPage(geographicalTerritories, geographicalTerritories.size(), 0, 0);
        }
    }
}