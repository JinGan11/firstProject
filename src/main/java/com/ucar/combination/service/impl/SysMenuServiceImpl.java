package com.ucar.combination.service.impl;

import com.ucar.combination.dao.SysMenuDao;
import com.ucar.combination.model.dto.MenuItemDto;
import com.ucar.combination.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:菜单管理
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-25
 **/
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    @Override
    public List<MenuItemDto> getValidMenu() {
        return sysMenuDao.getValidMenu();
    }
}
