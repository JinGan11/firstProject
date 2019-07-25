package com.ucar.combination.utils;


import com.ucar.combination.model.dto.MenuItemDto;
import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-25
 **/
public class TreeBuilder {

    List<MenuItemDto> nodes = new ArrayList<>();

    public String buildTree(List<MenuItemDto> nodes) {

        TreeBuilder treeBuilder = new TreeBuilder(nodes);

        return treeBuilder.buildJSONTree();
    }

    public TreeBuilder() {

    }

    public TreeBuilder(List<MenuItemDto> nodes) {
        super();
        this.nodes = nodes;
    }

    // 构建JSON树形结构
    public String buildJSONTree() {
        List<MenuItemDto> nodeTree = buildTree();
        JSONArray jsonArray = JSONArray.fromObject(nodeTree);
        return jsonArray.toString();
    }

    // 构建树形结构
    public List<MenuItemDto> buildTree() {
        List<MenuItemDto> treeNodes = new ArrayList<>();
        List<MenuItemDto> rootNodes = getRootNodes();
        for (MenuItemDto rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }

    // 递归子节点
    public void buildChildNodes(MenuItemDto node) {
        List<MenuItemDto> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for (MenuItemDto child : children) {
                buildChildNodes(child);
            }
            node.setChildren(children);
        }
    }

    // 获取父节点下所有的子节点
    public List<MenuItemDto> getChildNodes(MenuItemDto pnode) {
        List<MenuItemDto> childNodes = new ArrayList<>();
        for (MenuItemDto n : nodes) {
            if (pnode.getId().equals(n.getSuperId())) {
                childNodes.add(n);
            }
        }
        return childNodes;
    }

    // 判断是否为根节点
    public boolean rootNode(MenuItemDto node) {
        boolean isRootNode = true;
        for (MenuItemDto n : nodes) {
            if (node.getSuperId().equals(n.getId())) {
                isRootNode = false;
                break;
            }
        }
        return isRootNode;
    }

    // 获取集合中所有的根节点
    public List<MenuItemDto> getRootNodes() {
        List<MenuItemDto> rootNodes = new ArrayList<>();
        for (MenuItemDto n : nodes) {
            if (rootNode(n)) {
                rootNodes.add(n);
            }
        }
        return rootNodes;
    }
}
