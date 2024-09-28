package com.eroom.repositories;

import com.eroom.entities.MapRoleMenuSubPages;
import com.eroom.entities.MenuSubpages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuSubpagesRepository extends JpaRepository<MenuSubpages, Long> {

    boolean existsByNameAndStatus(String name, String status);

    List<MenuSubpages> findByStatusOrderById(String status);

    List<MenuSubpages> findByNameIn(List<String> menuNameList);

    List<MapRoleMenuSubPages> findByStatus(String status);


}