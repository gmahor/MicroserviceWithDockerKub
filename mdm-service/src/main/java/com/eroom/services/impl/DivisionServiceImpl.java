package com.eroom.services.impl;

import com.eroom.constants.GenericConstants;
import com.eroom.dtos.PageDTO;
import com.eroom.entities.Division;
import com.eroom.repositories.DivisionRepository;
import com.eroom.services.DivisionService;
import com.eroom.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DivisionServiceImpl implements DivisionService {

    private final DivisionRepository divisionRepository;

    private final CommonUtil commonUtil;

    @Autowired
    public DivisionServiceImpl(DivisionRepository divisionRepository,
                               CommonUtil commonUtil) {
        this.divisionRepository = divisionRepository;
        this.commonUtil = commonUtil;
    }

    @Override
    public PageDTO getAllDivision(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<Division> allDivision = divisionRepository.findAllByStatusOrderByDescription(pageable, GenericConstants.STATUS);
        return commonUtil.getDetailsPage(allDivision.getContent(), allDivision.getContent().size(), allDivision.getTotalPages(), allDivision.getTotalElements());
    }
}
