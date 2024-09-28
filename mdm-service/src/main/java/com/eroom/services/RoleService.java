package com.eroom.services;

import com.eroom.dtos.AddRoleDTO;
import com.eroom.dtos.UpdateRoleDTO;
import com.eroom.entities.Role;

import java.util.Set;

public interface RoleService {

    void creatRoleAutomatically();

    String addRole(AddRoleDTO addRoleDTO);

    Set<Role> getAllRoles();

    String updateRole(UpdateRoleDTO updateRoleDTO);

    String deleteRole(Long id);
}
