package com.ucar.combination.model.dto;

import java.util.List;
/**
 * description:菜单实体类
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-25
 **/
public class MenuItemDto {

    private Long id;
    private String url;
    private String title;
    private String icon;
    private Long superId;
    private List<MenuItemDto> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public List<MenuItemDto> getChildren() {
        return children;
    }

    public void setChildren(List<MenuItemDto> children) {
        this.children = children;
    }
}
