package com.eroom.services.impl;

import com.eroom.constants.GenericConstants;
import com.eroom.constants.MessageConstant;
import com.eroom.dtos.AddRoleDTO;
import com.eroom.dtos.UpdateRoleDTO;
import com.eroom.entities.Role;
import com.eroom.enums.RoleType;
import com.eroom.repositories.RoleRepository;
import com.eroom.services.RoleService;
import com.eroom.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final CommonUtil commonUtil;


    @Override
    public void creatRoleAutomatically() {
        RoleType[] roleTypes = RoleType.values();
        for (RoleType roleType : roleTypes) {
            Optional<Role> optionalRole = roleRepository.findByRoleNameAndStatus(roleType.value,
                    GenericConstants.STATUS);
            if (optionalRole.isEmpty()) {
                createRole(roleType);
            }
        }
    }

    private void createRole(RoleType roleType) {
        Role role = new Role();
        role.setCreatedBy(GenericConstants.E_ROOM_ADMIN_USERNAME);
        role.setModifiedBy(GenericConstants.E_ROOM_ADMIN_USERNAME);
        role.setRoleName(roleType.value);
        role.setStatus(GenericConstants.STATUS);
        Role savedRole = roleRepository.save(role);
        log.info(MessageConstant.ROLE_CREATED_SUCCESS, savedRole.getRoleName());
    }

    @Override
    public String addRole(AddRoleDTO addRoleDTO) {
        Role role = new Role();
        Optional<Role> roleName = roleRepository.findByRoleNameIgnoreCaseAndStatus(addRoleDTO.getRoleName(),
                GenericConstants.STATUS);
        if (roleName.isEmpty()) {
            role.setCreatedBy(commonUtil.loggedInUserName());
            role.setModifiedBy(commonUtil.loggedInUserName());
            role.setRoleName(addRoleDTO.getRoleName());
            roleRepository.save(role);
            return MessageConstant.ROLE_ADDED_SUCCESS;
        }
        return MessageConstant.ROLE_NAME_ALREADY_EXIST;
    }

    @Override
    public Set<Role> getAllRoles() {
        return roleRepository.findAllByStatusOrderByRoleName(GenericConstants.STATUS);
    }

    @Override
    public String updateRole(UpdateRoleDTO updateRoleDTO) {
        Optional<Role> optionalRole = roleRepository.findByIdAndStatus(updateRoleDTO.getRoleId(),
                GenericConstants.STATUS);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            role.setModifiedBy(commonUtil.loggedInUserName());
            role.setRoleName(updateRoleDTO.getRoleName());
            roleRepository.save(role);
            return MessageConstant.ROLE_UPDATED_SUCCESS;
        } else {
            return MessageConstant.ROLE_NOT_FOUND;
        }
    }

    @Override
    public String deleteRole(Long id) {
        Optional<Role> optionalRole = roleRepository.findByIdAndStatus(id, GenericConstants.STATUS);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            role.setModifiedBy(commonUtil.loggedInUserName());
            role.setStatus(GenericConstants.DELETE_STATUS);
            roleRepository.save(role);
            return MessageConstant.ROLE_DELETED_SUCCESS;
        }
        return MessageConstant.ROLE_NOT_FOUND;
    }
}
