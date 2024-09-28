package com.eroom.repositories;

import com.eroom.entities.RoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMappingRepository extends JpaRepository<RoleMapping, Long> {

}
