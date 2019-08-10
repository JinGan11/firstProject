package com.ucar.combination.controller;

import com.ucar.combination.common.Result;
import com.ucar.combination.model.Power;
import com.ucar.combination.model.dto.MenuItemDto;
import com.ucar.combination.service.SysMenuService;
import com.ucar.combination.utils.TreeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * description:导航栏菜单
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-25
 **/
@Controller
@CrossOrigin
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ResponseBody
    @RequestMapping("/list.do_")
    public Result list(HttpSession session){
        List powerList = (List<Power>) session.getAttribute("powerList");
        List<MenuItemDto> list = sysMenuService.getValidMenu();
        List<MenuItemDto> menuList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < powerList.size(); j++) {
                if (list.get(i).getId().equals(powerList.get(j))){
                    menuList.add(list.get(i));
                }
//                if (!list.get(i).getId().equals(powerList.get(j)) && powerList.size()-1 == j) {
//                    list.remove(i);
//                }
            }
        }
        TreeBuilder treeBuilder = new TreeBuilder(menuList);
        return new Result().ok().put("menuList",treeBuilder.buildTree());
    }
}
