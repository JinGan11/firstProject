package com.ucar.combination.model;

import com.ucar.combination.model.dto.BaseBean;

/**
 * @version 1.0
 * @description: 权限类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-06 10:51
 */
public class Power extends BaseBean {

    private Long powerId;
    private String powerName;
    private Long superId;
    private Long sortId;

    public Long getPowerId() {
        return powerId;
    }

    public void setPowerId(Long powerId) {
        this.powerId = powerId;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }
}
