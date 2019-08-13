package com.ucar.combination.utils;

import com.ucar.combination.model.dto.DepartmentTree2Dto;

import java.util.List;

/**
 * description:支持业务线相关的工具类
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 2.0
 * @date 2019/8/7 15:32
 **/
public class SupportBusiness2Util {

    /**
     * description: 比较两个业务线的内容
     *
     * @return 0：两者相等。1：supports1包含supports2。-1：supports1不包含supports2。
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/7 15:34
     * @params String字符串，内容格式“买买车&专车&闪贷”
     */
    public static int compareSups(String supports1, String supports2) {
        String[] sups1 = supports1.split("&");
        String[] sups2 = supports2.split("&");
        if (sups1.length < sups2.length) return -1;
        Boolean find_flag = false; // 标志该轮遍历查询是否找到
        for (String tmp2 : sups2) {
            find_flag = false;
            for (String tmp1 : sups1) {
                if (tmp1.equals(tmp2)) {
                    find_flag = true;
                    break;
                }
            }
            if (!find_flag) return -1;
        }
        if (sups1.length == sups2.length) return 0;
        else return 1;
    }

    /**
     * description: 根据支持业务线设置list中每个属性的canChoose，用于修改上级部门
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/7 19:50
     * @params DepartmentUppeDto的list，选中的部门的id
     */
    public static List<DepartmentTree2Dto> setCanChooseBySupports(List<DepartmentTree2Dto> list, Long choosedId) {
        // 获取选择的部门的业务线
        String supports = "";
        for (DepartmentTree2Dto item : list) {
            if (item.getId().equals(choosedId)) {
                supports = item.getSupportBusiness();
                break;
            }
        }
        if ("".equals(supports)) return list; // 没有支持的业务线，所以业务线不会对它修改上级部门产生影响。
        // 循环设置canChoose
        for (int i = 0; i < list.size(); i++) {
            if (compareSups(list.get(i).getSupportBusiness(), supports) >= 0)
                list.get(i).setCanChoose(true);
            else
                list.get(i).setCanChoose(false);
        }
        return list;
    }

}
