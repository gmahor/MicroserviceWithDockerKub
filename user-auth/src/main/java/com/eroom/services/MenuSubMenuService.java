package com.eroom.services;

import com.eroom.dtos.MenuSubMenuDataDTO;

import java.util.List;

public interface MenuSubMenuService {


	List<MenuSubMenuDataDTO> menuSubMenu(List<Long> roleId);
}
