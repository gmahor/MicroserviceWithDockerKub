package com.eroom.services;

import com.eroom.dtos.*;

import java.util.List;

public interface UserService {

    void createDefaultAdmin();

    String addUsersDetails(AddUserDTO addUserDto);

    PageDTO getUserDetails(Integer pageNo, Integer size);

    String deleteUserDetails(Long userId);

    String userDetailsUpdate(UserUpdateRespondDTO userUpdateRespondDto);

    UserDetailRespDTO getUserDetailsById(Long userId);

    List<FunctionalUserMapUsers> getFunctionalUserList(List<String> departments, List<String> divisions, List<String> supplierVerticals);

    List<ApproverUserMapUsers> getAppoverUserList(List<String> departments, List<String> divisions, List<String> supplierVerticals);

    List<CheckerUserMapUsers> getCheckerUserList(List<String> departments, List<String> divisions, List<String> supplierVerticals);

    void createAdminRoleMapMenuPages();

    List<ProjectManagerMappingWithUsers> getProjectManagerList(List<String> departments, List<String> divisions, List<String> supplierVerticals);


}
