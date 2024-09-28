package com.eroom.repositories;

import com.eroom.entities.MapRoleMenuSubPages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRoleMenuSubPagesRepository extends JpaRepository<MapRoleMenuSubPages, Long> {
	List<MapRoleMenuSubPages> findByRoleIdInAndStatusOrderByIdDesc(List<Long> roleId, String userStatus);
}
