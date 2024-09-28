package com.eroom.services.impl;

import com.eroom.constants.GenericConstants;
import com.eroom.constants.MessageConstant;
import com.eroom.dtos.*;
import com.eroom.entities.*;
import com.eroom.enums.RoleType;
import com.eroom.repositories.*;
import com.eroom.services.UserService;
import com.eroom.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final RoleMappingRepository roleMappingRepository;

    private final CommonUtil commonUtil;

    private final UserMappingWithGeographyRepo userMappingWithGeographyRepo;

    private final UserMappingWithSupplyVerticalRepo userMappingWithSupplyVerticalRepo;

    private final UserMappingWithDivisionRepo userMappingWithDivisionRepo;

    private final UserMappingWithDepartmentRepo userMappingWithDepartmentRepo;

    private final MenuSubpagesRepository menuSubpagesRepository;

    private final MapRoleMenuSubPagesRepository mapRoleMenuSubPagesRepository;

    private final DivisionRepository divisionRepository;

    private final SupplyVerticalRepository supplyVerticalRepository;

    private final GeographicalTerritoryRepository geographicalTerritoryRepository;

    @Override
    public void createDefaultAdmin() {
        Optional<User> optionalUser = userRepository.findByUsernameAndStatus(GenericConstants.E_ROOM_ADMIN_USERNAME,
                GenericConstants.STATUS);
        if (optionalUser.isEmpty()) {
            User adminUser = new User();
            adminUser.setCreatedBy(GenericConstants.E_ROOM_ADMIN_USERNAME);
            adminUser.setModifiedBy(GenericConstants.E_ROOM_ADMIN_USERNAME);
            adminUser.setEmail(GenericConstants.E_ROOM_ADMIN_EMAIL);
            adminUser.setUsername(GenericConstants.E_ROOM_ADMIN_USERNAME);
            adminUser.setCreatedOn(LocalDateTime.now());
            adminUser.setModifiedOn(LocalDateTime.now());
            adminUser.setPassword(passwordEncoder.encode(GenericConstants.E_ROOM_ADMIN_PASSWORD));
            Set<RoleMapping> roleMappings = mappedAdminUserRoles(
                    roleRepository.findAllByStatus(GenericConstants.STATUS));
            adminUser.setRoles(roleMappings);
            mappedAdminDivisionAndSupplyVerticalAndGeography(adminUser);
            log.info(MessageConstant.DEFAULT_USER_SUCCESS);
        }
    }

    public void mappedAdminDivisionAndSupplyVerticalAndGeography(User adminUser) {
        try {
            List<Division> allDivision = divisionRepository.findAll();
            List<SupplyVertical> allSupplyVerticals = supplyVerticalRepository.findAll();
            List<GeographicalTerritory> geographicalTerritoryList = geographicalTerritoryRepository.findAll();
            Set<UserMappingWithDivision> userMappingWithDivisionSet = allDivision.stream().map(division -> {
                UserMappingWithDivision userMappingWithDivision = new UserMappingWithDivision();
                userMappingWithDivision.setDivision(division.getDivisionCode());
                userMappingWithDivision.setDivisionCode(division.getDivisionCode());
                return userMappingWithDivision;
            }).collect(Collectors.toSet());
            adminUser.setUserMappingWithDivision(userMappingWithDivisionSet);

            Set<UserMappingWithSupplyVertical> userMappingWithSupplyVerticalSet = allSupplyVerticals.stream().map(supplyVertical -> {
                UserMappingWithSupplyVertical userMappingWithSupplyVertical = new UserMappingWithSupplyVertical();
                userMappingWithSupplyVertical.setSupplyVertical(supplyVertical.getSupplyVertical());
                return userMappingWithSupplyVertical;
            }).collect(Collectors.toSet());
            adminUser.setUserMappingWithSupplyVertical(userMappingWithSupplyVerticalSet);

            Set<UserMappingWithGeography> userMappingWithGeographySet = geographicalTerritoryList.stream().map(geographicalTerritory -> {
                UserMappingWithGeography userMappingWithGeography = new UserMappingWithGeography();
                userMappingWithGeography.setGeography(geographicalTerritory.getGeographyDesc());
                return userMappingWithGeography;
            }).collect(Collectors.toSet());
            adminUser.setUserMappingWithGeography(userMappingWithGeographySet);

            userRepository.save(adminUser);
        } catch (Exception e) {
            log.error("Error while saving division and supply vertical data for admin. - ", e);
        }
    }

    @Override
    public void createAdminRoleMapMenuPages() {
        Optional<User> optionalUser = userRepository.findByUsernameAndStatus(GenericConstants.E_ROOM_ADMIN_USERNAME,
                GenericConstants.STATUS);
        if (optionalUser.isPresent()) {
            List<MapRoleMenuSubPages> mapRoleMenuSubList = mapRoleMenuSubPagesRepository
                    .findByStatus(GenericConstants.STATUS);
            if (mapRoleMenuSubList.isEmpty()) {
                List<MapRoleMenuSubPages> mapRoleMenuSubPagesList = new ArrayList<>();
                List<MenuSubpages> menuSubPagesList = menuSubpagesRepository
                        .findByStatusOrderById(GenericConstants.STATUS);
                menuSubPagesList.forEach(menuSubPage -> {
                    MapRoleMenuSubPages mapRoleMenuSubPages = new MapRoleMenuSubPages();
                    mapRoleMenuSubPages.setCreatedOn(LocalDateTime.now());
                    mapRoleMenuSubPages.setModifiedOn(LocalDateTime.now());
                    mapRoleMenuSubPages.setCreatedBy(GenericConstants.E_ROOM_ADMIN_USERNAME);
                    mapRoleMenuSubPages.setModifiedBy(GenericConstants.E_ROOM_ADMIN_USERNAME);
                    mapRoleMenuSubPages.setRoleId(1L);
                    mapRoleMenuSubPages.setStatus(GenericConstants.STATUS);
                    mapRoleMenuSubPages.setMenuSubPagesId(menuSubPage.getMenuId());
                    mapRoleMenuSubPagesList.add(mapRoleMenuSubPages);
                });
                mapRoleMenuSubPagesRepository.saveAll(mapRoleMenuSubPagesList);
            }
        }
    }

    private Set<RoleMapping> mappedAdminUserRoles(Set<Role> roles) {
        Set<RoleMapping> userRoles = new HashSet<>();
        roles.forEach(role -> {
            RoleMapping roleMapping = new RoleMapping();
            roleMapping.setCreatedBy(GenericConstants.E_ROOM_ADMIN_USERNAME);
            roleMapping.setModifiedBy(GenericConstants.E_ROOM_ADMIN_USERNAME);
            roleMapping.setRoleId(role.getId());
            roleMapping.setRoleName(role.getRoleName());
            userRoles.add(roleMapping);
        });
        return userRoles;
    }

    @Override
    public String addUsersDetails(AddUserDTO addUserDto) {
        User userObj = new User();
        Optional<User> userOptional = userRepository.findByUsernameIgnoreCaseAndStatus(addUserDto.getUsername(),
                GenericConstants.STATUS);
        if (userOptional.isEmpty()) {
            userObj.setPassword(passwordEncoder.encode(addUserDto.getPassword()));
            userObj.setStatus(GenericConstants.STATUS);
            userObj.setUsername(addUserDto.getUsername());
            userObj.setEmployeeName(addUserDto.getEmployeeName());
            userObj.setEmployeeMasterId(addUserDto.getEmployeeMasterId());
            userObj.setEmail(addUserDto.getEmail());
            userObj.setCreatedBy(commonUtil.loggedInUserName());
            userObj.setModifiedBy(commonUtil.loggedInUserName());
            userObj.setEmail(addUserDto.getEmail());
            userObj.setRoles(getAllRoleMapping(addUserDto.getRoleList()));
            setAndSaveAllMappingData(addUserDto, userObj);
            return MessageConstant.USER_ADDED_SUCCESS;
        } else {
            return MessageConstant.USER_NAME_ALREAD_EXIST;
        }
    }

    public void setAndSaveAllMappingData(AddUserDTO addUserDto, User userObj) {
        CompletableFuture<Set<UserMappingWithSupplyVertical>> mappedSupplyVerticalDataFuture = userMappingWithSupplyVertical(addUserDto.getSupplyVerticalList());
        CompletableFuture<Set<UserMappingWithGeography>> mappedGeographyDataFuture = userMappingWithGeography(addUserDto.getGeographyList());
        CompletableFuture<Set<UserMappingWithDivision>> mappedDivisionDataFuture = userMappingWithDivision(addUserDto.getDivisionList());
        CompletableFuture<Set<UserMappingWithDepartment>> mappedDepartmentDataFuture = userMappingWithDepartment(addUserDto.getDepartmentList());
        userRepository.save(userObj);
        CompletableFuture.allOf(
                mappedSupplyVerticalDataFuture, mappedGeographyDataFuture,
                mappedDivisionDataFuture, mappedDepartmentDataFuture
        ).thenRun(() -> {
            try {
                Set<UserMappingWithDivision> userMappingWithDivisions = mappedDivisionDataFuture.get();
                if (!userMappingWithDivisions.isEmpty()) {
                    userObj.setUserMappingWithDivision(userMappingWithDivisions);
                }

                Set<UserMappingWithGeography> userMappingWithGeographies = mappedGeographyDataFuture.get();
                if (!userMappingWithGeographies.isEmpty()) {
                    userObj.setUserMappingWithGeography(userMappingWithGeographies);
                }

                Set<UserMappingWithSupplyVertical> userMappingWithSupplyVerticals = mappedSupplyVerticalDataFuture.get();
                if (!userMappingWithSupplyVerticals.isEmpty()) {
                    userObj.setUserMappingWithSupplyVertical(userMappingWithSupplyVerticals);
                }
                Set<UserMappingWithDepartment> userMappingWithDepartments = mappedDepartmentDataFuture.get();
                if (!userMappingWithDepartments.isEmpty()) {
                    userObj.setUserMappingWithDepartment(userMappingWithDepartments);
                }
                userRepository.save(userObj);
                log.info("User all mapping saved successfully.");
            } catch (InterruptedException e) {
                log.error("Interrupted exception occur in setAndSaveAllMappingData method - ", e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                log.error("setAndSaveAllMappingData getting error ", e);
            }
        });
    }

    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<UserMappingWithDepartment>> userMappingWithDepartment(List<DepartmentDTO> departmentDtos) {
        Set<UserMappingWithDepartment> mappedDepartmentData = departmentDtos.stream().map(departmentDto -> {
            UserMappingWithDepartment userMappingWithDepartment = new UserMappingWithDepartment();
            userMappingWithDepartment.setDepartmentCode(departmentDto.getDepartmentCode());
            userMappingWithDepartment.setDepartmentDesc(departmentDto.getDepartmentDesc());
            return userMappingWithDepartment;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappedDepartmentData);
    }

    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<UserMappingWithSupplyVertical>> userMappingWithSupplyVertical(
            List<SupplyVerticalDTO> supplyVerticalDtos) {
        Set<UserMappingWithSupplyVertical> mappedDivisionData = supplyVerticalDtos.stream().map(supplyVerticalDto -> {
            UserMappingWithSupplyVertical userMappingWithSupplyVertical = new UserMappingWithSupplyVertical();
            userMappingWithSupplyVertical.setSupplyVertical(supplyVerticalDto.getSupplyVertical());
            return userMappingWithSupplyVertical;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappedDivisionData);
    }

    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<UserMappingWithGeography>> userMappingWithGeography(List<GeographyDTO> geographyDtos) {
        Set<UserMappingWithGeography> mappedGeographyData = geographyDtos.stream().map(geographyDto -> {
            UserMappingWithGeography userMappingWithGeography = new UserMappingWithGeography();
            userMappingWithGeography.setGeography(geographyDto.getGeography());
            return userMappingWithGeography;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappedGeographyData);
    }

    @Async(value = "mdmAsyncExecutor")
    public CompletableFuture<Set<UserMappingWithDivision>> userMappingWithDivision(List<DivisionDTO> divisionDtoList) {
        Set<UserMappingWithDivision> mappedDivisionData = divisionDtoList.stream().map(divisionDto -> {
            UserMappingWithDivision userMappingWithDivision = new UserMappingWithDivision();
            userMappingWithDivision.setDivision(divisionDto.getDivision());
            userMappingWithDivision.setDivisionCode(divisionDto.getDivisionCode());
            return userMappingWithDivision;
        }).collect(Collectors.toSet());
        return CompletableFuture.completedFuture(mappedDivisionData);
    }

    private Set<RoleMapping> getAllRoleMapping(List<RoleMapUserDTO> roleMapUserDtos) {
        Set<RoleMapping> roleMappings = new HashSet<>();
        roleMapUserDtos.forEach(roleIds -> {
            Optional<Role> optionalRole = roleRepository.findByIdAndStatus(roleIds.getRoleId(),
                    GenericConstants.STATUS);
            if (optionalRole.isPresent()) {
                Role role = optionalRole.get();
                RoleMapping roleMapping = new RoleMapping();
                roleMapping.setCreatedBy(commonUtil.loggedInUserName());
                roleMapping.setModifiedBy(commonUtil.loggedInUserName());
                roleMapping.setRoleId(role.getId());
                roleMapping.setRoleName(role.getRoleName());
                roleMappings.add(roleMapping);
            }
        });
        return roleMappings;
    }

    @Override
    public PageDTO getUserDetails(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<UserDetailRespDTO> allUserDetails = userRepository.findAllByStatusOrderByIdDesc(pageable, GenericConstants.STATUS);
        return commonUtil.getDetailsPage(allUserDetails.getContent(), allUserDetails.getContent().size(),
                allUserDetails.getTotalPages(), allUserDetails.getTotalElements());
    }


    @Override
    public String userDetailsUpdate(UserUpdateRespondDTO userUpdateRespondDto) {
        Optional<User> userOptional = userRepository.findByIdAndStatus(userUpdateRespondDto.getUserId(),
                GenericConstants.STATUS);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setModifiedBy(commonUtil.loggedInUserName());
            user.setEmail(userUpdateRespondDto.getEmail() != null && !userUpdateRespondDto.getEmail().isEmpty()
                    ? userUpdateRespondDto.getEmail()
                    : user.getEmail());
            user.setEmployeeName(
                    userUpdateRespondDto.getEmployeeName() != null && !userUpdateRespondDto.getEmployeeName().isEmpty()
                            ? userUpdateRespondDto.getEmployeeName()
                            : user.getEmployeeName());
            user.setEmployeeMasterId(userUpdateRespondDto.getEmployeeMasterId() != null
                    && userUpdateRespondDto.getEmployeeMasterId() != 0L ? userUpdateRespondDto.getEmployeeMasterId()
                    : user.getEmployeeMasterId());
            user.setUsername(userUpdateRespondDto.getUsername() != null && !userUpdateRespondDto.getUsername().isEmpty()
                    ? userUpdateRespondDto.getUsername()
                    : user.getUsername());
            if (userUpdateRespondDto.getPassword() != null && !userUpdateRespondDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userUpdateRespondDto.getPassword()));
            }
            if (userUpdateRespondDto.getRoleList() != null && !userUpdateRespondDto.getRoleList().isEmpty()) {
                user.setRoles(updateRoleMapping(userUpdateRespondDto.getRoleList()));
            }
            userRepository.save(user);
            updatingAllUserMappedData(userUpdateRespondDto, user);
            return MessageConstant.USER_DETAILS_UPDATED;
        }
        return MessageConstant.USER_DETAILS_NOT_FOUND_WITH_REQUEST_ID;
    }

    public void updatingAllUserMappedData(UserUpdateRespondDTO userUpdateRespondDto, User userObj) {

        // Update user division
        updateUserDivisionDetails(userUpdateRespondDto.getDivisionList(), userObj);

        // Update user supply vertical
        updateSupplyVerticalDetails(userUpdateRespondDto.getSupplyVerticalList(), userObj);

        // Update user geography
        updateGeographyDetails(userUpdateRespondDto.getGeographyList(), userObj);

        // Update user department
        updateDepartmentDetails(userUpdateRespondDto.getDepartmentList(), userObj);

        userRepository.save(userObj);
    }


    private void updateUserDivisionDetails(List<DivisionDTO> divisionList, User userObj) {
        try {
            if (divisionList != null && !divisionList.isEmpty()) {
                CompletableFuture<Set<UserMappingWithDivision>> updatedUserDivisionData = userMappingWithDivision(divisionList);
                if (updatedUserDivisionData.isDone()) {
                    Set<UserMappingWithDivision> userMappingWithDivisions = updatedUserDivisionData.get();
                    inActiveDivisionMappingAsync(userObj.getUserMappingWithDivision());
                    userObj.setUserMappingWithDivision(userMappingWithDivisions);
                }
            }
        } catch (InterruptedException e) {
            log.error("Interrupted exception while updating user division - ", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error while updating user division - ", e);
        }
    }

    private void updateSupplyVerticalDetails(List<SupplyVerticalDTO> supplyVerticalList, User userObj) {
        try {
            if (supplyVerticalList != null && !supplyVerticalList.isEmpty()) {
                CompletableFuture<Set<UserMappingWithSupplyVertical>> updatedUserSupplyVerticalData = userMappingWithSupplyVertical(supplyVerticalList);
                if (updatedUserSupplyVerticalData.isDone()) {
                    Set<UserMappingWithSupplyVertical> userMappingWithSupplyVerticals = updatedUserSupplyVerticalData.get();
                    inActiveSupplyVerticalMappingAsync(userObj.getUserMappingWithSupplyVertical());
                    userObj.setUserMappingWithSupplyVertical(userMappingWithSupplyVerticals);
                }
            }
        } catch (InterruptedException e) {
            log.error("Interrupted exception while updating user supply vertical - ", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error while updating user supply vertical - ", e);
        }

    }

    private void updateGeographyDetails(List<GeographyDTO> geographyList, User userObj) {
        try {
            if (geographyList != null && !geographyList.isEmpty()) {
                CompletableFuture<Set<UserMappingWithGeography>> updatedUserGeographyData = userMappingWithGeography(geographyList);
                if (updatedUserGeographyData.isDone()) {
                    Set<UserMappingWithGeography> userMappingWithGeographies = updatedUserGeographyData.get();
                    inActiveGeographyMappingAsync(userObj.getUserMappingWithGeography());
                    userObj.setUserMappingWithGeography(userMappingWithGeographies);
                }
            }
        } catch (InterruptedException e) {
            log.error("Interrupted exception while updating user geography - ", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error while updating user geography - ", e);
        }
    }

    private void updateDepartmentDetails(List<DepartmentDTO> departmentList, User userObj) {
        try {
            if (departmentList != null && !departmentList.isEmpty()) {
                CompletableFuture<Set<UserMappingWithDepartment>> updatedUserDepartmentData = userMappingWithDepartment(departmentList);
                if (updatedUserDepartmentData.isDone()) {
                    inActiveDepartmentMappingAsync(userObj.getUserMappingWithDepartment());
                    Set<UserMappingWithDepartment> userMappingWithDepartments = updatedUserDepartmentData.get();
                    userObj.setUserMappingWithDepartment(userMappingWithDepartments);
                }
            }
        } catch (InterruptedException e) {
            log.error("Interrupted exception while updating user department - ", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error while updating user department - ", e);
        }
    }

    private Set<RoleMapping> updateRoleMapping(List<RoleMapUserDTO> roleList) {
        Set<RoleMapping> roleMappings = new HashSet<>();
        roleList.forEach(roleId -> {
            Optional<Role> optionalRole = roleRepository.findByIdAndStatus(roleId.getRoleId(), GenericConstants.STATUS);
            if (optionalRole.isPresent()) {
                Role role = optionalRole.get();
                RoleMapping roleMapping = new RoleMapping();
                roleMapping.setModifiedBy(commonUtil.loggedInUserName());
                roleMapping.setCreatedBy(commonUtil.loggedInUserName());
                roleMapping.setRoleId(role.getId());
                roleMapping.setRoleName(role.getRoleName());
                roleMappings.add(roleMapping);
            }
        });
        return roleMappings;
    }

    public String deleteUserDetails(Long userId) {
        Optional<User> userOptional = userRepository.findByIdAndStatus(userId, GenericConstants.STATUS);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            deleteUserAndUserDetails(user);
            return MessageConstant.USER_DELETED;
        }
        return MessageConstant.NOT_FOUND_ACTIVE_USER;
    }

    public void deleteUserAndUserDetails(User user) {
        user.setStatus(GenericConstants.IN_ACTIVE_STATUS);
        inActiveDepartmentMappingAsync(user.getUserMappingWithDepartment());
        inActiveRoleMappingAsync(user.getRoles());
        inActiveDivisionMappingAsync(user.getUserMappingWithDivision());
        inActiveSupplyVerticalMappingAsync(user.getUserMappingWithSupplyVertical());
        inActiveGeographyMappingAsync(user.getUserMappingWithGeography());
        userRepository.save(user);
    }

    @Async(value = "mdmAsyncExecutor")
    public void inActiveDepartmentMappingAsync(Set<UserMappingWithDepartment> userMappingWithDepartmentList) {
        CompletableFuture.runAsync(() -> {
            try {
                Set<UserMappingWithDepartment> userMappingWithDepartmentsSet = userMappingWithDepartmentList
                        .stream()
                        .peek(userMappingWithDepartment -> userMappingWithDepartment.setUserId(null)).collect(Collectors.toSet());
                userMappingWithDepartmentRepo.saveAll(userMappingWithDepartmentsSet);
            } catch (Exception e) {
                log.error("Error in processing department mapping asynchronously", e);
            }
        });
    }

    @Async(value = "mdmAsyncExecutor")
    public void inActiveGeographyMappingAsync(Set<UserMappingWithGeography> userMappingWithGeography) {
        CompletableFuture.runAsync(() -> {
            try {
                Set<UserMappingWithGeography> userMappingWithGeographySet = userMappingWithGeography
                        .stream()
                        .peek(userMapWithGeography -> userMapWithGeography.setUserId(null)).collect(Collectors.toSet());
                userMappingWithGeographyRepo.saveAll(userMappingWithGeographySet);
            } catch (Exception e) {
                log.error("Error in processing geography mapping asynchronously", e);
            }
        });
    }

    @Async(value = "mdmAsyncExecutor")
    public void inActiveSupplyVerticalMappingAsync(
            Set<UserMappingWithSupplyVertical> userMappingWithSupplyVertical) {
        CompletableFuture.runAsync(() -> {
            try {
                Set<UserMappingWithSupplyVertical> userMappingWithSupplyVerticalSet = userMappingWithSupplyVertical
                        .stream()
                        .peek(userMappingWithSupply -> userMappingWithSupply.setUserId(null)).collect(Collectors.toSet());
                userMappingWithSupplyVerticalRepo.saveAll(userMappingWithSupplyVerticalSet);
            } catch (Exception e) {
                log.error("Error in processing supply vertical mapping asynchronously", e);
            }
        });
    }

    @Async(value = "mdmAsyncExecutor")
    public void inActiveDivisionMappingAsync(Set<UserMappingWithDivision> userMappingWithDivision) {
        CompletableFuture.runAsync(() -> {
            try {
                Set<UserMappingWithDivision> userMappingWithDivisionSet = userMappingWithDivision
                        .stream()
                        .peek(userMappingDivision -> userMappingDivision.setUserId(null)).collect(Collectors.toSet());
                userMappingWithDivisionRepo.saveAll(userMappingWithDivisionSet);
            } catch (Exception e) {
                log.error("Error in processing division mapping asynchronously", e);
            }
        });
    }

    @Async(value = "mdmAsyncExecutor")
    public void inActiveRoleMappingAsync(Set<RoleMapping> roleMappings) {
        CompletableFuture.runAsync(() -> {
            try {
                Set<RoleMapping> roleMappingSet = roleMappings
                        .stream()
                        .peek(roleMapping -> roleMapping.setStatus(GenericConstants.IN_ACTIVE_STATUS)).collect(Collectors.toSet());
                roleMappingRepository.saveAll(roleMappingSet);
            } catch (Exception e) {
                log.error("Error in processing role mapping asynchronously", e);
            }
        });
    }

    @Override
    public UserDetailRespDTO getUserDetailsById(Long userId) {
        return userRepository.findRespByIdAndStatus(userId, GenericConstants.STATUS).orElse(null);
    }

    @Override
    public List<FunctionalUserMapUsers> getFunctionalUserList(List<String> departments, List<String> divisions, List<String> supplierVerticals) {
        List<FunctionalUserMapUsers> functionalUserList = new ArrayList<>();
        userRepository.getFunctionalUserList(GenericConstants.FUNCTIONAL_USER, departments, divisions, supplierVerticals, GenericConstants.STATUS)
                .forEach(functionalUserMapUsers -> {
                    Optional<User> optionalUser = userRepository.findByIdAndStatus(functionalUserMapUsers.getId(), GenericConstants.STATUS);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        if (!GenericConstants.E_ROOM_ADMIN_USERNAME.equals(user.getUsername())) {
                            functionalUserList.add(functionalUserMapUsers);
                        }
                    }
                });
        return functionalUserList;
    }

    @Override
    public List<ApproverUserMapUsers> getAppoverUserList(List<String> departments, List<String> divisions, List<String> supplierVerticals) {
        List<ApproverUserMapUsers> approverUserList = new ArrayList<>();
        userRepository.getApproverUserList(RoleType.Approver.value, departments, divisions, supplierVerticals, GenericConstants.STATUS)
                .forEach(approverUserMapUsers -> {
                    Optional<User> optionalUser = userRepository.findByIdAndStatus(approverUserMapUsers.getId(), GenericConstants.STATUS);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        if (!GenericConstants.E_ROOM_ADMIN_USERNAME.equals(user.getUsername())) {
                            approverUserList.add(approverUserMapUsers);
                        }
                    }
                });
        return approverUserList;
    }

    @Override
    public List<CheckerUserMapUsers> getCheckerUserList(List<String> departments, List<String> divisions, List<String> supplierVerticals) {
        List<CheckerUserMapUsers> checkerUserList = new ArrayList<>();
        userRepository.getCheckerUserList(GenericConstants.CHECKER_USER, departments, divisions, supplierVerticals, GenericConstants.STATUS)
                .forEach(checkerUser -> {
                    Optional<User> optionalUser = userRepository.findByIdAndStatus(checkerUser.getId(), GenericConstants.STATUS);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        if (!GenericConstants.E_ROOM_ADMIN_USERNAME.equals(user.getUsername())) {
                            checkerUserList.add(checkerUser);
                        }
                    }
                });
        return checkerUserList;
    }

    @Override
    public List<ProjectManagerMappingWithUsers> getProjectManagerList(List<String> departments, List<String> divisions, List<String> supplierVerticals) {
        List<ProjectManagerMappingWithUsers> projectManagerUserList = new ArrayList<>();
        userRepository.getProjectManagerList(GenericConstants.PROJECT_MANAGER, departments, divisions, supplierVerticals, GenericConstants.STATUS)
                .forEach(projectManagerUser -> {
                    Optional<User> optionalUser = userRepository.findByIdAndStatus(projectManagerUser.getId(), GenericConstants.STATUS);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        if (!GenericConstants.E_ROOM_ADMIN_USERNAME.equals(user.getUsername())) {
                            projectManagerUserList.add(projectManagerUser);
                        }
                    }
                });
        return projectManagerUserList;
    }

}
