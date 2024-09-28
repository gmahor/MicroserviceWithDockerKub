package com.eroom.repositories;

import com.eroom.dtos.RoleBasedMenuPagesDbRespo;
import com.eroom.entities.MapRoleMenuSubPages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRoleMenuSubPagesRepository extends JpaRepository<MapRoleMenuSubPages, Long> {

    List<MapRoleMenuSubPages> findByRoleIdAndStatusOrderById(Long roleId, String status);

    @Query(nativeQuery = true, value = "SELECT Min(roles.id) as roleId, roles.role_name as roleName, STRING_AGG(msp.name, ', ') AS pageNames\r\n"
            + "					FROM map_roles_menu_sub_pages AS mrmsp INNER JOIN roles ON roles.id = mrmsp.role_id\r\n"
            + "					INNER JOIN menu_sub_pages AS msp ON msp.id = mrmsp.menu_sub_pages_id\r\n"
            + "					WHERE mrmsp.status =:status GROUP BY roles.role_name")
    Page<RoleBasedMenuPagesDbRespo> getRoleBasedMenuPages(Pageable pageable,
                                                          @Param(value = "status") String status);

    List<MapRoleMenuSubPages> findByRoleIdAndStatus(Long roleId, String status);

    List<MapRoleMenuSubPages> findByStatus(String status);

}