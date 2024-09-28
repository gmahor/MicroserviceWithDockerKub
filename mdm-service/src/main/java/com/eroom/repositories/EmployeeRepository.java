package com.eroom.repositories;

import com.eroom.dtos.EmployeeRespDTO;
import com.eroom.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findAllByStatus(Pageable pageable, String status);

    @Query(value = "SELECT DISTINCT emp_id as id, employeeNumber FROM (SELECT emp_id, CONCAT(employee_number, '-', employee_name) AS employeeNumber FROM employee_master) AS subquery ORDER BY employeeNumber ASC;", nativeQuery = true)
    List<EmployeeRespDTO> getEmployeeNumberList();

    Optional<Employee> findByEmpId(String empId);
}
