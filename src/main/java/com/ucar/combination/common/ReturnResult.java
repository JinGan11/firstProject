package com.ucar.combination.common;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: combination
 * @description: 返回结果集
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-07-30 16:51
 */
public class ReturnResult {

    private boolean result;
    private int code;
    private String msg;
    private List<?> list;
    private PageInfo<?> pageInfo;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public PageInfo<?> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<?> pageInfo) {
        this.pageInfo = pageInfo;
    }
}
