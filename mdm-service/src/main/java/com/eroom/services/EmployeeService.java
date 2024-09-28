package com.eroom.services;

import com.eroom.dtos.DepartmentMappingDTO;
import com.eroom.dtos.EmployeeRespondDTO;
import com.eroom.dtos.PageDTO;
import com.eroom.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    PageDTO getAllEmployees(Integer pageNo, Integer size);

    List<EmployeeRespondDTO> getEmployeeNumberList();

    Optional<Employee> getEmployeeUsername(String employeeId);

    List<DepartmentMappingDTO> getDepartmentList();

}
