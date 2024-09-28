package com.eroom.services.impl;

import com.eroom.constants.GenericConstants;
import com.eroom.constants.MessageConstant;
import com.eroom.dtos.MenuDTO;
import com.eroom.dtos.MenuSubPagesDTO;
import com.eroom.entities.Menu;
import com.eroom.entities.MenuSubpages;
import com.eroom.repositories.MenuRepository;
import com.eroom.repositories.MenuSubpagesRepository;
import com.eroom.services.MenuService;
import com.eroom.services.ModelMapperService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    private final MenuSubpagesRepository menuSubpagesRepository;

    private final ModelMapperService modelMapper;


    @Override
    public void createAllMenuIfNotExists() {
        List<MenuDTO> menuDTOList = new ArrayList<>();
        MenuDTO docType = new MenuDTO(GenericConstants.E_ROOM_ADMIN_USERNAME, GenericConstants.E_ROOM_ADMIN_USERNAME,
                "Document Type", "0", 2, null);
        MenuDTO geography = new MenuDTO(GenericConstants.E_ROOM_ADMIN_USERNAME, GenericConstants.E_ROOM_ADMIN_USERNAME,
                "Geography", "0", 3, null);
        MenuDTO supplyVertical = new MenuDTO(GenericConstants.E_ROOM_ADMIN_USERNAME,
                GenericConstants.E_ROOM_ADMIN_USERNAME, "Supply Vertical", "0", 4, null);
        MenuDTO divine = new MenuDTO(GenericConstants.E_ROOM_ADMIN_USERNAME, GenericConstants.E_ROOM_ADMIN_USERNAME,
                "Division", "0", 5, null);
        MenuDTO userDetails = new MenuDTO(GenericConstants.E_ROOM_ADMIN_USERNAME,
                GenericConstants.E_ROOM_ADMIN_USERNAME, "User Details", "0", 6, null);
        MenuDTO documentAssignment = new MenuDTO(GenericConstants.E_ROOM_ADMIN_USERNAME,
                GenericConstants.E_ROOM_ADMIN_USERNAME, "Document Assignment", "0", 7, null);
        MenuDTO role = new MenuDTO(GenericConstants.E_ROOM_ADMIN_USERNAME, GenericConstants.E_ROOM_ADMIN_USERNAME,
                "Role", "0", 8, null);
        MenuDTO documentAssignmentApproval = new MenuDTO(GenericConstants.E_ROOM_ADMIN_USERNAME,
                GenericConstants.E_ROOM_ADMIN_USERNAME, "Document Assignment Approval", "1", 10, null);
        menuDTOList.add(docType);
        menuDTOList.add(geography);
        menuDTOList.add(supplyVertical);
        menuDTOList.add(divine);
        menuDTOList.add(userDetails);
        menuDTOList.add(documentAssignment);
        menuDTOList.add(role);
        menuDTOList.add(documentAssignmentApproval);
        menuDTOList.forEach(this::createMenu);
    }

    private void createMenu(MenuDTO menuDTO) {
        boolean exists = menuRepository.existsByNameAndStatus(menuDTO.getName(), GenericConstants.STATUS);
        if (!exists) {
            Menu menu = modelMapper.map(menuDTO, Menu.class);
            menuRepository.save(menu);
            log.info(MessageConstant.MENU_ADDED_SUCCESS);
        }
    }

    @Override
    public void createMenuSubPagesIfNotExists() {
        List<Menu> menus = menuRepository.findAllByStatus(GenericConstants.STATUS);
        setMenuSubPagesData(menus);
    }

    private void setMenuSubPagesData(List<Menu> menus) {
        menus.forEach(menu -> {
            switch (menu.getName()) {
                case "Document Type":
                    MenuSubPagesDTO docTypeSubPage = new MenuSubPagesDTO(GenericConstants.E_ROOM_ADMIN_USERNAME,
                            GenericConstants.E_ROOM_ADMIN_USERNAME, "/document_type", menu.getName(), menu.getId(), 1, null,
                            menu);
                    boolean docTypeSubPageExists = menuSubpagesRepository.existsByNameAndStatus(docTypeSubPage.getName(),
                            GenericConstants.STATUS);
                    if (!docTypeSubPageExists) {
                        createMenuSubPage(docTypeSubPage, menu);
                    }
                    break;
                case "Geography":
                    MenuSubPagesDTO geographySubPage = new MenuSubPagesDTO(GenericConstants.E_ROOM_ADMIN_USERNAME,
                            GenericConstants.E_ROOM_ADMIN_USERNAME, "/geography", menu.getName(), menu.getId(), 1, null,
                            menu);
                    boolean geographySubPageExists = menuSubpagesRepository
                            .existsByNameAndStatus(geographySubPage.getName(), GenericConstants.STATUS);
                    if (!geographySubPageExists) {
                        createMenuSubPage(geographySubPage, menu);
                    }
                    break;
                case "Supply Vertical":
                    MenuSubPagesDTO supplyVerticalSubPage = new MenuSubPagesDTO(GenericConstants.E_ROOM_ADMIN_USERNAME,
                            GenericConstants.E_ROOM_ADMIN_USERNAME, "/supply_vertical", menu.getName(), menu.getId(), 1,
                            null, menu);
                    boolean supplyVerticalSubPageExists = menuSubpagesRepository
                            .existsByNameAndStatus(supplyVerticalSubPage.getName(), GenericConstants.STATUS);
                    if (!supplyVerticalSubPageExists) {
                        createMenuSubPage(supplyVerticalSubPage, menu);
                    }
                    break;
                case "Division":
                    MenuSubPagesDTO divisionSubPage = new MenuSubPagesDTO(GenericConstants.E_ROOM_ADMIN_USERNAME,
                            GenericConstants.E_ROOM_ADMIN_USERNAME, "/division", menu.getName(), menu.getId(), 1, null,
                            menu);
                    boolean divisionSubPageExists = menuSubpagesRepository.existsByNameAndStatus(divisionSubPage.getName(),
                            GenericConstants.STATUS);
                    if (!divisionSubPageExists) {
                        createMenuSubPage(divisionSubPage, menu);
                    }
                    break;
                case "User Details":
                    MenuSubPagesDTO userDetailsSubPage = new MenuSubPagesDTO(GenericConstants.E_ROOM_ADMIN_USERNAME,
                            GenericConstants.E_ROOM_ADMIN_USERNAME, "/user_details", menu.getName(), menu.getId(), 1, null,
                            menu);
                    boolean userDetailsSubPageExists = menuSubpagesRepository
                            .existsByNameAndStatus(userDetailsSubPage.getName(), GenericConstants.STATUS);
                    if (!userDetailsSubPageExists) {
                        createMenuSubPage(userDetailsSubPage, menu);
                    }
                    break;
                case "Document Assignment":
                    MenuSubPagesDTO docAssignmentSubPage = new MenuSubPagesDTO(GenericConstants.E_ROOM_ADMIN_USERNAME,
                            GenericConstants.E_ROOM_ADMIN_USERNAME, "/document_assignment", menu.getName(), menu.getId(), 1,
                            null, menu);
                    boolean docAssignmentSubPageExists = menuSubpagesRepository
                            .existsByNameAndStatus(docAssignmentSubPage.getName(), GenericConstants.STATUS);
                    if (!docAssignmentSubPageExists) {
                        createMenuSubPage(docAssignmentSubPage, menu);
                    }
                    break;
                case "Document Assignment Approval":
                    MenuSubPagesDTO documentAssignmentSubPage = new MenuSubPagesDTO(GenericConstants.E_ROOM_ADMIN_USERNAME,
                            GenericConstants.E_ROOM_ADMIN_USERNAME, "/document_assigment_approval", menu.getName(),
                            menu.getId(), 1, null, menu);
                    boolean documentAssignmentSubPageExists = menuSubpagesRepository
                            .existsByNameAndStatus(documentAssignmentSubPage.getName(), GenericConstants.STATUS);
                    if (!documentAssignmentSubPageExists) {
                        createMenuSubPage(documentAssignmentSubPage, menu);
                    }
                    break;
                case "Role":
                    MenuSubPagesDTO roleSubPage = new MenuSubPagesDTO(GenericConstants.E_ROOM_ADMIN_USERNAME,
                            GenericConstants.E_ROOM_ADMIN_USERNAME, "/role", menu.getName(), menu.getId(), 1, null, menu);
                    boolean roleSubPageExists = menuSubpagesRepository.existsByNameAndStatus(roleSubPage.getName(),
                            GenericConstants.STATUS);
                    if (!roleSubPageExists) {
                        createMenuSubPage(roleSubPage, menu);
                    }
                    break;
                default:
                    log.info("Menu Sub Page not data set.");
            }
        });
    }

    private void createMenuSubPage(MenuSubPagesDTO menuSubPagesDTO, Menu menu) {
        MenuSubpages menuSubpages = new MenuSubpages();
        menuSubpages.setCreatedBy(GenericConstants.E_ROOM_ADMIN_USERNAME);
        menuSubpages.setModifiedBy(GenericConstants.E_ROOM_ADMIN_USERNAME);
        menuSubpages.setUrl(menuSubPagesDTO.getUrl());
        menuSubpages.setName(menuSubPagesDTO.getName());
        menuSubpages.setMenuId(menuSubPagesDTO.getMenuId());
        menuSubpages.setSubMenuOrder(menuSubPagesDTO.getSubMenuOrder());
        menuSubpages.setSubMenuIcon(menuSubPagesDTO.getSubMenuIcon());
        menuSubpages.setMenu(menu);
        menuSubpagesRepository.save(menuSubpages);
        log.info(MessageConstant.MENU_ADDED_SUCCESS);
    }
}