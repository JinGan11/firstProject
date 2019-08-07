package com.ucar.combination.model;

import java.util.List;

/**
 * @version 1.0
 * @description: 封装权限列表
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-06 10:56
 */
public class PowerList {
    public List<Power> powerList;

    public List<Power> getPowerList() {
        return powerList;
    }

    public void setPowerList(List<Power> powerList) {
        this.powerList = powerList;
    }
}
