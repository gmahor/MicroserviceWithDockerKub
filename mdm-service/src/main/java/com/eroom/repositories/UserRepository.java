package com.eroom.repositories;

import com.eroom.dtos.*;
import com.eroom.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndStatus(String username, String status);

    Optional<User> findByIdAndStatus(Long id, String status);

    Optional<UserDetailRespDTO> findRespByIdAndStatus(Long id, String status);

    Optional<User> findByUsernameIgnoreCaseAndStatus(String username, String status);

    Page<UserDetailRespDTO> findAllByStatusOrderByIdDesc(Pageable pageable, String status);

    @Query(value = "select DISTINCT users.id,concat(users.username,'-',users.employee_name) as username ,user_roles.role_name as roleName from user_roles \n" +
            "inner join users on users.id=user_roles.user_id\n" +
            "inner join users_mapping_department umd on umd.user_id = users.id\n" +
            "inner join users_mapping_division umdd on umdd.user_id = users.id\n" +
            "inner join users_mapping_supply_vertical umsv on umsv.user_id = users.id\n" +
            "where  user_roles.role_name=:functionalUser and umd.department_code  in (:departments) and \n" +
            "umdd.division_code in (:divisions) and umdd.user_id is not null and\n" +
            "umsv.supply_vertical in (:supplyVerticals) and umsv.user_id is not null and users.status=:status", nativeQuery = true)
    List<FunctionalUserMapUsers> getFunctionalUserList(@Param("functionalUser") String functionalUser,
                                                       @Param("departments") List<String> departments,
                                                       @Param("divisions") List<String> divisions,
                                                       @Param("supplyVerticals") List<String> supplierVerticals,
                                                       @Param("status") String status);

    @Query(value = "select DISTINCT users.id,concat(users.username,'-',users.employee_name) as approver ,user_roles.role_name as roleName from user_roles \n" +
            "inner join users on users.id=user_roles.user_id\n" +
            "inner join users_mapping_department umd on umd.user_id = users.id\n" +
            "inner join users_mapping_division umdd on umdd.user_id = users.id\n" +
            "inner join users_mapping_supply_vertical umsv on umsv.user_id = users.id\n" +
            "where  user_roles.role_name=:approverUser and umd.department_code  in (:departments) and \n" +
            "umdd.division_code in (:divisions) and umdd.user_id is not null and\n" +
            "umsv.supply_vertical in (:supplyVerticals) and umsv.user_id is not null and users.status=:status", nativeQuery = true)
    List<ApproverUserMapUsers> getApproverUserList(@Param("approverUser") String approverUser,
                                                   @Param("departments") List<String> departments,
                                                   @Param("divisions") List<String> divisions,
                                                   @Param("supplyVerticals") List<String> supplierVerticals,
                                                   @Param("status") String status);

    @Query(value = "select DISTINCT users.id,concat(users.username,'-',users.employee_name) as checker ,user_roles.role_name as roleName from user_roles \n" +
            "inner join users on users.id=user_roles.user_id\n" +
            "inner join users_mapping_department umd on umd.user_id = users.id\n" +
            "inner join users_mapping_division umdd on umdd.user_id = users.id\n" +
            "inner join users_mapping_supply_vertical umsv on umsv.user_id = users.id\n" +
            "where  user_roles.role_name=:checkerUser and umd.department_code  in (:departments) and \n" +
            "umdd.division_code in (:divisions) and umdd.user_id is not null and\n" +
            "umsv.supply_vertical in (:supplyVerticals) and umsv.user_id is not null and users.status=:status", nativeQuery = true)
    List<CheckerUserMapUsers> getCheckerUserList(@Param("checkerUser") String checkerUser,
                                                 @Param("departments") List<String> departments,
                                                 @Param("divisions") List<String> divisions,
                                                 @Param("supplyVerticals") List<String> supplierVerticals,
                                                 @Param("status") String status);

    @Query(value = "select DISTINCT users.id,concat(users.username,'-',users.employee_name) as projectManager  from user_roles \n" +
            "inner join users on users.id=user_roles.user_id\n" +
            "inner join users_mapping_department umd on umd.user_id = users.id\n" +
            "inner join users_mapping_division umdd on umdd.user_id = users.id\n" +
            "inner join users_mapping_supply_vertical umsv on umsv.user_id = users.id\n" +
            "where  user_roles.role_name=:projectManager and umd.department_code  in (:departments) and \n" +
            "umdd.division_code in (:divisions) and umdd.user_id is not null and\n" +
            "umsv.supply_vertical in (:supplyVerticals) and umsv.user_id is not null and users.status=:status", nativeQuery = true)
    List<ProjectManagerMappingWithUsers> getProjectManagerList(@Param("projectManager") String projectManager,
                                                               @Param("departments") List<String> departments,
                                                               @Param("divisions") List<String> divisions,
                                                               @Param("supplyVerticals") List<String> supplierVerticals,
                                                               @Param("status") String status);

}
