package com.eroom.services.impl;

import com.eroom.constants.GenericConstants;
import com.eroom.constants.MessageConstant;
import com.eroom.dtos.AddSupplyVerticalDTO;
import com.eroom.dtos.PageDTO;
import com.eroom.dtos.UpdateSupplyVerticalDTO;
import com.eroom.entities.SupplyVertical;
import com.eroom.repositories.SupplyVerticalRepository;
import com.eroom.services.SupplyVerticalService;
import com.eroom.utils.CommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SupplyVerticalServiceImpl implements SupplyVerticalService {

    private final SupplyVerticalRepository supplyVerticalRepository;

    private final CommonUtil commonUtil;


    @Override
    public String addSupplyVertical(AddSupplyVerticalDTO addSupplyVerticalDTO) {
        SupplyVertical supplyVertical = new SupplyVertical();
        supplyVertical.setSupplyVertical(addSupplyVerticalDTO.getSupplyVertical());
        supplyVerticalRepository.save(supplyVertical);
        return MessageConstant.SUPPLY_VERTICAL_SAVE_SUCCESS;
    }

    @Override
    public PageDTO getAllSupplyVerticals(Integer pageNo, Integer size, String searchSupplyVertical) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<SupplyVertical> allSupplyVerticals;
        if (searchSupplyVertical != null) {
            allSupplyVerticals = supplyVerticalRepository.findAllBySupplyVerticalIgnoreCaseAndStatusOrderByIdDesc(
                    pageable, searchSupplyVertical, GenericConstants.STATUS);
        } else {
            allSupplyVerticals = supplyVerticalRepository.findAllByStatusOrderByIdDesc(pageable,
                    GenericConstants.STATUS);
        }
        return commonUtil.getDetailsPage(allSupplyVerticals.getContent(), allSupplyVerticals.getContent().size(),
                allSupplyVerticals.getTotalPages(), allSupplyVerticals.getTotalElements());
    }

    @Override
    public String updateSupplyVertical(UpdateSupplyVerticalDTO updateSupplyVerticalDTO) {
        Optional<SupplyVertical> optionalSupplyVertical = supplyVerticalRepository
                .findByIdAndStatus(updateSupplyVerticalDTO.getId(), GenericConstants.STATUS);
        if (optionalSupplyVertical.isPresent()) {
            SupplyVertical supplyVertical = optionalSupplyVertical.get();
            supplyVertical.setSupplyVertical(updateSupplyVerticalDTO.getSupplyVertical());
            supplyVerticalRepository.save(supplyVertical);
            return MessageConstant.SUPPLY_VERTICAL_UPDATED_SUCCESS;
        }
        return MessageConstant.SUPPLY_VERTICAL_NOT_FOUND_WITH_THIS_ID;
    }

    @Override
    public String deleteSupplyVertical(Long id) {
        Optional<SupplyVertical> optionalSupplyVertical = supplyVerticalRepository.findByIdAndStatus(id,
                GenericConstants.STATUS);
        if (optionalSupplyVertical.isPresent()) {
            SupplyVertical supplyVertical = optionalSupplyVertical.get();
            supplyVertical.setStatus(GenericConstants.IN_ACTIVE_STATUS);
            supplyVerticalRepository.save(supplyVertical);
            return MessageConstant.SUPPLY_VERTICAL_DELETED_SUCCESS;
        }
        return MessageConstant.SUPPLY_VERTICAL_NOT_FOUND_WITH_THIS_ID;
    }
}