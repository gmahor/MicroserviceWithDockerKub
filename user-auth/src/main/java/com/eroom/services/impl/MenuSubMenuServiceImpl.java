package com.eroom.services.impl;

import com.eroom.constants.GenericConstants;
import com.eroom.dtos.MapRoleMenuSubPagesDTO;
import com.eroom.dtos.MenuSubMenuDataDTO;
import com.eroom.repositories.MapRoleMenuSubPagesRepository;
import com.eroom.services.MenuSubMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MenuSubMenuServiceImpl implements MenuSubMenuService {

    private final MapRoleMenuSubPagesRepository mapRoleMenuSubPagesRepository;

    @Override
    public List<MenuSubMenuDataDTO> menuSubMenu(List<Long> roleId) {
        List<MapRoleMenuSubPagesDTO> menuList = mapRoleMenuSubPagesRepository
                .findByRoleIdInAndStatusOrderByIdDesc(roleId, GenericConstants.USER_STATUS).stream()
                .map(mapRoleMenuSubPages -> {
                    MapRoleMenuSubPagesDTO pageDtoObj = new MapRoleMenuSubPagesDTO();
                    pageDtoObj.setRoleName(mapRoleMenuSubPages.getRole().getRoleName());
                    pageDtoObj.setMenuSubPageName(mapRoleMenuSubPages.getMenuSubPages().getName());
                    pageDtoObj.setMenuIcon(mapRoleMenuSubPages.getMenuSubPages().getMenu().getMenuIcon());
                    pageDtoObj.setMenuSubPageUrl(mapRoleMenuSubPages.getMenuSubPages().getUrl());
                    pageDtoObj.setMenuName(mapRoleMenuSubPages.getMenuSubPages().getMenu().getName());
                    pageDtoObj.setMenuOrder(mapRoleMenuSubPages.getMenuSubPages().getMenu().getMenuOrder());
                    pageDtoObj.setSubMenuOrder(mapRoleMenuSubPages.getMenuSubPages().getSubMenuOrder());
                    pageDtoObj.setIsDropDown(mapRoleMenuSubPages.getMenuSubPages().getMenu().getIsDropDown());
                    pageDtoObj.setSubMenuIcon(mapRoleMenuSubPages.getMenuSubPages().getSubMenuIcon());
                    return pageDtoObj;
                }).toList().stream()
                .sorted(Comparator.comparingInt(MapRoleMenuSubPagesDTO::getMenuOrder)
                        .thenComparingInt(MapRoleMenuSubPagesDTO::getSubMenuOrder))
                .toList();
        List<String> pageList = menuList.stream().map(MapRoleMenuSubPagesDTO::getMenuSubPageUrl)
                .toList();
        if (!pageList.isEmpty()) {
            String[] menuArray = menuList.stream().map(MapRoleMenuSubPagesDTO::getMenuName).distinct()
                    .toArray(String[]::new);
            return Arrays.stream(menuArray).map(menuName -> {
                MenuSubMenuDataDTO menuSubMenuDataDTO = new MenuSubMenuDataDTO();
                menuSubMenuDataDTO.setMenuName(menuName);
                List<MapRoleMenuSubPagesDTO> mappedList = menuList.stream()
                        .filter(o -> o.getMenuName().equals(menuName)).collect(Collectors.toList());
                menuSubMenuDataDTO.setIsDropDown(mappedList.get(0).getIsDropDown());
                menuSubMenuDataDTO.setMenuIcon(mappedList.get(0).getMenuIcon());
                menuSubMenuDataDTO.setSubMenuList(mappedList);
                return menuSubMenuDataDTO;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
