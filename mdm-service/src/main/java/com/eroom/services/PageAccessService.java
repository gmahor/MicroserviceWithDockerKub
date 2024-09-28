package com.eroom.services;

import com.eroom.dtos.MenuDataDTO;
import com.eroom.dtos.PageDTO;
import com.eroom.dtos.UserAccessPageRightDTO;
import com.eroom.dtos.UserAccessPageRightUpdateDTO;
import com.eroom.entities.MapRoleMenuSubPages;

import java.util.List;

public interface PageAccessService {

    String addPageAccessRight(UserAccessPageRightDTO userAccessPageRightDTO);

    List<MenuDataDTO> getMenuSubPagesList();

	PageDTO getMenuAndSubMenuPagesWithRole(Integer pageNo,Integer size);

	List<MapRoleMenuSubPages> updateMenuAndSubMenuPages(Long roleId);

	String deleteMenuAndSubMenuPageByRoleId(Long roleId);

	String updatePageAccessRight(UserAccessPageRightUpdateDTO userAccessPageRightUpdateDto);

	

}
