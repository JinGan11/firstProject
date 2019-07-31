package com.ucar.combination.model.dto;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * description:基类
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-31 13:25:08
 **/
@MappedSuperclass
public class BaseBean implements Serializable {
    //创建人id
    protected Long createEmp;
    //创建时间
    protected Date createTime;
    //修改人id
    protected Long modifyEmp;
    //修改时间
    protected Date modifyTime;

    public Long getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(Long createEmp) {
        this.createEmp = createEmp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifyEmp() {
        return modifyEmp;
    }

    public void setModifyEmp(Long modifyEmp) {
        this.modifyEmp = modifyEmp;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
