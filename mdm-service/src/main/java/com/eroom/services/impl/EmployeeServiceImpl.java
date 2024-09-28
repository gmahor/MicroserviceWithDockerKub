package com.eroom.services.impl;

import com.eroom.constants.GenericConstants;
import com.eroom.dtos.DepartmentMappingDTO;
import com.eroom.dtos.EmployeeRespondDTO;
import com.eroom.dtos.PageDTO;
import com.eroom.entities.Employee;
import com.eroom.repositories.CostCentreMasterRepository;
import com.eroom.repositories.EmployeeRepository;
import com.eroom.services.EmployeeService;
import com.eroom.utils.CommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final CommonUtil commonUtil;

    private final CostCentreMasterRepository costCentreMasterRepository;

    @Override
    public PageDTO getAllEmployees(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<Employee> employees = employeeRepository.findAllByStatus(pageable, GenericConstants.STATUS);
        return commonUtil.getDetailsPage(employees.getContent(), employees.getContent().size(),
                employees.getTotalPages(), employees.getTotalElements());
    }

    @Override
    public List<EmployeeRespondDTO> getEmployeeNumberList() {
        return employeeRepository.getEmployeeNumberList()
                .stream()
                .map(employeeRespDTO -> {
                    EmployeeRespondDTO employeeRespondDto = new EmployeeRespondDTO();
                    employeeRespondDto.setId(employeeRespDTO.getId());
                    employeeRespondDto.setEmployeeNumber(employeeRespDTO.getEmployeeNumber());
                    return employeeRespondDto;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<Employee> getEmployeeUsername(String employeeId) {
        return employeeRepository.findByEmpId(employeeId);
    }

    @Override
    public List<DepartmentMappingDTO> getDepartmentList() {
        return costCentreMasterRepository.getDepartmentList();
    }
}
