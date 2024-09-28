package com.eroom.repositories;

import com.eroom.dtos.DepartmentMappingDTO;
import com.eroom.entities.CostCentreMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostCentreMasterRepository extends JpaRepository<CostCentreMaster, Long> {

    @Query(value = "select DISTINCT concat(employee_master.segment4 ,'-',cost_centre_master.child_desc) as department from employee_master\n" +
            "inner join cost_centre_master on cost_centre_master.child_code=employee_master.segment4", nativeQuery = true)
    List<DepartmentMappingDTO> getDepartmentList();

}
