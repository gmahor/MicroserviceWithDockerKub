package com.eroom.services;

import com.eroom.dtos.PageDTO;

public interface GeographicalTerritoryService {

    PageDTO getAllGeographicalTerritory(Integer pageNo, Integer size);

}