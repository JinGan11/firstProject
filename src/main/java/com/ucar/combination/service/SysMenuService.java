package com.ucar.combination.service;


import com.ucar.combination.model.dto.MenuItemDto;

import java.util.List;

/**
 * description:
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-25
 **/
public interface SysMenuService {

    List<MenuItemDto> getValidMenu();
}
