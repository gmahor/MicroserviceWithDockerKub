package com.eroom.services.impl;

import com.eroom.entities.Organization;
import com.eroom.repositories.OrganisationRepository;
import com.eroom.services.OrganisationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class OrganisationServiceImpl implements OrganisationService {

    private final OrganisationRepository organisationRepository;

    @Override
    public List<Organization> organisationList() {
        return organisationRepository.findAll();
    }

}
