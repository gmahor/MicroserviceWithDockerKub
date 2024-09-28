package com.eroom.services;

import com.eroom.dtos.AddSupplyVerticalDTO;
import com.eroom.dtos.PageDTO;
import com.eroom.dtos.UpdateSupplyVerticalDTO;

public interface SupplyVerticalService {

    String addSupplyVertical(AddSupplyVerticalDTO addSupplyVerticalDTO);

    PageDTO getAllSupplyVerticals(Integer pageNo, Integer size,String searchSupplyVerticalCode);

    String updateSupplyVertical(UpdateSupplyVerticalDTO updateSupplyVerticalDTO);

    String deleteSupplyVertical(Long id);

}