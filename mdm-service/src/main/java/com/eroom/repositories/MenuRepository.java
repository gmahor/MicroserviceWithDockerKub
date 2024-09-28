package com.eroom.repositories;

import com.eroom.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    boolean existsByNameAndStatus(String name, String status);

    List<Menu> findAllByStatus(String integer);
}