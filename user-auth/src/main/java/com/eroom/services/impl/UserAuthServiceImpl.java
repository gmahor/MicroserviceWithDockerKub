package com.eroom.services.impl;

import com.eroom.constants.GenericConstants;
import com.eroom.constants.MessageConstant;
import com.eroom.dtos.SignInDTO;
import com.eroom.dtos.SignInRespDTO;
import com.eroom.entities.LoginHistory;
import com.eroom.entities.RoleMapping;
import com.eroom.entities.User;
import com.eroom.enums.RoleType;
import com.eroom.repositories.LoginHistoryRepository;
import com.eroom.repositories.UserRepository;
import com.eroom.services.MenuSubMenuService;
import com.eroom.services.UserAuthService;
import com.eroom.utils.CommonUtil;
import com.eroom.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;

    private final LoginHistoryRepository loginHistoryRepository;

    private final JwtUtil jwtUtil;

    private final CommonUtil commonUtil;

    private final MenuSubMenuService menuSubMenuService;


    @Override
    public Object signIn(SignInDTO signInDTO) {
        Optional<User> optionalUser = userRepository.findByUsernameAndStatus(signInDTO.getUsername(),
                GenericConstants.USER_STATUS);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String token = jwtUtil.getToken(user);
            if (token != null) {
                SignInRespDTO signInRespDTO = new SignInRespDTO();
                signInRespDTO.setUserId(user.getId());
                signInRespDTO.setUsername(user.getUsername());
                signInRespDTO.setRoles(arrangeUserRoleInCorrectManner(user));
                signInRespDTO.setToken(token);
                List<Long> roleId = user.getRoles().stream().map(RoleMapping::getRoleId)
                        .collect(Collectors.toList());
                signInRespDTO.setMenuSubMenuDataDTOS(menuSubMenuService.menuSubMenu(roleId));
                saveLoginDetails(user);
                return signInRespDTO;
            }
            return MessageConstant.ERROR_WHILE_CREATING_TOKEN;
        }
        return MessageConstant.USER_NOT_FOUND_WITH_THIS_USERNAME;
    }

    private static List<String> arrangeUserRoleInCorrectManner(User user) {
        List<String> predefinedOrder;
        if (GenericConstants.E_ROOM_ADMIN_USERNAME.equals(user.getUsername())) {
            predefinedOrder = Arrays.asList(
                    RoleType.Admin.value,
                    RoleType.Functional_User.value,
                    RoleType.Checker.value,
                    RoleType.Approver.value,
                    RoleType.Project_Manager.value
            );
        } else {
            predefinedOrder = Arrays.asList(
                    RoleType.Functional_User.value,
                    RoleType.Checker.value,
                    RoleType.Approver.value,
                    RoleType.Project_Manager.value
            );
        }
        return user.getRoles().stream()
                .map(RoleMapping::getRoleName)
                .sorted(Comparator.comparingInt(predefinedOrder::indexOf))
                .collect(Collectors.toList());
    }

    private void saveLoginDetails(User user) {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setCreatedBy(user.getUsername());
        loginHistory.setModifiedBy(user.getUsername());
        loginHistory.setLoginTime(LocalDateTime.now());
        loginHistory.setStatus(GenericConstants.USER_STATUS);
        loginHistory.setUser(user);
        loginHistoryRepository.save(loginHistory);
        log.info("Login history save successfully");
    }

    @Override
    public String logout(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<LoginHistory> loginHistories = loginHistoryRepository.findByUserAndLogoutTimeIsNull(user);
            loginHistories.forEach(loginHistory -> {
                loginHistory.setModifiedBy(user.getUsername());
                loginHistory.setLogoutTime(LocalDateTime.now());
                loginHistoryRepository.save(loginHistory);
                log.info("Logout Time updated successfully");
            });
            deleteLoginHistory(user);
            return MessageConstant.USER_LOGOUT_SUCCESS;
        }
        return MessageConstant.USER_NOT_FOUND;
    }

    private void deleteLoginHistory(User user) {
        loginHistoryRepository.deleteLoginHistoryDetails(user.getId(), GenericConstants.LOGIN_HISTORIES_LIMIT);
        log.info("Deleted login histories successfully");
    }
}
