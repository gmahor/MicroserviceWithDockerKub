package com.eroom.services.impl;

import com.eroom.constants.GenericConstants;
import com.eroom.constants.MessageConstant;
import com.eroom.dtos.*;
import com.eroom.entities.MapRoleMenuSubPages;
import com.eroom.entities.MenuSubpages;
import com.eroom.repositories.MapRoleMenuSubPagesRepository;
import com.eroom.repositories.MenuSubpagesRepository;
import com.eroom.services.PageAccessService;
import com.eroom.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class PageAccessServiceImpl implements PageAccessService {
    private final MenuSubpagesRepository menuSubpagesRepository;

    private final MapRoleMenuSubPagesRepository mapRoleMenuSubPagesRepository;

    private final CommonUtil commonUtil;


    @Override
    public String addPageAccessRight(UserAccessPageRightDTO userAccessPageRightDTO) {
        List<MapRoleMenuSubPages> updateMapRoleMenuSubPagesList = mapRoleMenuSubPagesRepository
                .findByRoleIdAndStatusOrderById(userAccessPageRightDTO.getRoleId(), GenericConstants.STATUS).stream()
                .peek(mapRoleMenuSubPages -> {
                    mapRoleMenuSubPages.setModifiedBy(commonUtil.loggedInUserName());
                    mapRoleMenuSubPages.setStatus(GenericConstants.IN_ACTIVE_STATUS);
                }).collect(Collectors.toList());
        mapRoleMenuSubPagesRepository.saveAll(updateMapRoleMenuSubPagesList);

        List<MapRoleMenuSubPages> updatedMapRoleMenuSubPagesList = userAccessPageRightDTO.getMenuIdList().stream()
                .map(menuSubpageId -> {
                    MapRoleMenuSubPages updateMapRoleMenu = new MapRoleMenuSubPages();
                    updateMapRoleMenu.setCreatedBy(commonUtil.loggedInUserName());
                    updateMapRoleMenu.setModifiedBy(commonUtil.loggedInUserName());
                    updateMapRoleMenu.setRoleId(userAccessPageRightDTO.getRoleId());
                    updateMapRoleMenu.setMenuSubPagesId(menuSubpageId);
                    updateMapRoleMenu.setStatus(GenericConstants.STATUS);
                    return updateMapRoleMenu;
                }).collect(Collectors.toList());
        mapRoleMenuSubPagesRepository.saveAll(updatedMapRoleMenuSubPagesList);
        return MessageConstant.PAGE_ACCESS_RIGHT_SAVED_SUCCESS;
    }

    @Override
    public List<MenuDataDTO> getMenuSubPagesList() {
        return menuSubpagesRepository.findByStatusOrderById(GenericConstants.STATUS).stream().map(menuSubpages -> {
            MenuDataDTO menuDataDTO = new MenuDataDTO();
            menuDataDTO.setManuSubPageId(menuSubpages.getId());
            menuDataDTO.setMenuName(menuSubpages.getName());
            return menuDataDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public PageDTO getMenuAndSubMenuPagesWithRole(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<RoleBasedMenuPagesDbRespo> pagesDbRespoPage = mapRoleMenuSubPagesRepository.getRoleBasedMenuPages(pageable, GenericConstants.STATUS);
        return commonUtil.getDetailsPage(pagesDbRespoPage.getContent(), pagesDbRespoPage.getContent().size(),
                pagesDbRespoPage.getTotalPages(), pagesDbRespoPage.getTotalElements());
    }

    @Override
    public List<MapRoleMenuSubPages> updateMenuAndSubMenuPages(Long roleId) {
        // TODO Auto-generated method stub
        return mapRoleMenuSubPagesRepository.findByRoleIdAndStatus(roleId, GenericConstants.STATUS);
    }

    @Override
    public String deleteMenuAndSubMenuPageByRoleId(Long roleId) {
        List<MapRoleMenuSubPages> mapRoleMenuSubPages = mapRoleMenuSubPagesRepository.findByRoleIdAndStatus(roleId,
                GenericConstants.STATUS);
        if (!mapRoleMenuSubPages.isEmpty()) {
            mapRoleMenuSubPages.forEach(mapRoleMenuSubPage -> mapRoleMenuSubPage.setStatus(GenericConstants.IN_ACTIVE_STATUS));
            List<MapRoleMenuSubPages> updatedEntities = mapRoleMenuSubPagesRepository.saveAll(mapRoleMenuSubPages);
            if (!updatedEntities.isEmpty()) {
                return MessageConstant.DELETED_MENU_SUB_MENU_PAGE;
            }
        }
        return MessageConstant.ERROR_WHILE_DELETE_MENU_SUB_MENU_PAGES;
    }

    @Override
    public String updatePageAccessRight(UserAccessPageRightUpdateDTO userAccessPageRightUpdateDto) {
        List<MapRoleMenuSubPages> updateMapRoleMenuSubPagesList = mapRoleMenuSubPagesRepository
                .findByRoleIdAndStatusOrderById(userAccessPageRightUpdateDto.getRoleId(), GenericConstants.STATUS)
                .stream().peek(mapRoleMenuSubPages -> {
                    mapRoleMenuSubPages.setModifiedBy(commonUtil.loggedInUserName());
                    mapRoleMenuSubPages.setStatus(GenericConstants.IN_ACTIVE_STATUS);
                }).collect(Collectors.toList());
        mapRoleMenuSubPagesRepository.saveAll(updateMapRoleMenuSubPagesList);
        List<MenuSubpages> menuSubpageList = menuSubpagesRepository
                .findByNameIn(userAccessPageRightUpdateDto.getMenuNameList());
        menuSubpageList.forEach(menuSubmenuPage -> {
            MapRoleMenuSubPages updateMapRoleMenu = new MapRoleMenuSubPages();
            updateMapRoleMenu.setCreatedBy(commonUtil.loggedInUserName());
            updateMapRoleMenu.setModifiedBy(commonUtil.loggedInUserName());
            updateMapRoleMenu.setRoleId(userAccessPageRightUpdateDto.getRoleId());
            updateMapRoleMenu.setMenuSubPagesId(menuSubmenuPage.getMenuId());
            updateMapRoleMenu.setStatus(GenericConstants.STATUS);
            mapRoleMenuSubPagesRepository.save(updateMapRoleMenu);
        });
        return MessageConstant.PAGE_ACCESS_RIGHT_UPDATED_SUCCESS;
    }
}