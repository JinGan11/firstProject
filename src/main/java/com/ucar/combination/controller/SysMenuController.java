package com.ucar.combination.controller;

import com.ucar.combination.common.Result;
import com.ucar.combination.model.dto.MenuItemDto;
import com.ucar.combination.service.SysMenuService;
import com.ucar.combination.utils.TreeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * description:
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-25
 **/
@Controller
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ResponseBody
    @RequestMapping("/list.do_")
    public Result list(){
        List<MenuItemDto> list = sysMenuService.getValidMenu();
        TreeBuilder treeBuilder = new TreeBuilder(list);
        return new Result().ok().put("menuList",treeBuilder.buildTree());
    }
}
