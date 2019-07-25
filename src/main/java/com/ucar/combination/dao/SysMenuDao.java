package com.ucar.combination.dao;


import com.ucar.combination.model.dto.MenuItemDto;

import java.util.List;
/**
 * description:
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-25
 **/
public interface SysMenuDao {

    List<MenuItemDto> getValidMenu();
}
