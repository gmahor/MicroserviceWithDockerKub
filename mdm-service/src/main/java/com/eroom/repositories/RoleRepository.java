package com.eroom.repositories;

import com.eroom.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleNameAndStatus(String roleType, String status);

    Set<Role> findAllByStatus(String status);

    Optional<Role> findByIdAndStatus(Long id, String status);

    Optional<Role> findByRoleNameIgnoreCaseAndStatus(String roleName, String status);

    Set<Role> findAllByStatusOrderByRoleName(String status);

}
