package com.ucar.combination.utils;

import com.ucar.combination.model.PowerTree;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @description: 权限树构造器
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-05 16:57
 */
public class PowerTreeBuilder {

    // 根节点的编号
    private static final Long rootPowerId = 1L;

    private List<PowerTree> list;
    private PowerTree rootNode;

    public PowerTree buildTree(List<PowerTree> treeDtos) {
        list = treeDtos;
        findRoot();
        findChildren(rootNode);
        return rootNode;
    }

    // 找根节点
    private void findRoot() {
        for (PowerTree node : list) {
            if (rootPowerId.equals(node.getSortId())) {
                rootNode = node;
                break;
            }
        }
    }

    // 递归查找子节点
    private PowerTree findChildren(PowerTree node) {
        List<PowerTree> tmpList = new ArrayList<>();
        for (PowerTree tmpNode : list) {
            if (node.getSortId() != null && node.getSortId() != 0 && tmpNode.getSuperId() != null) {
                if (node.getSortId().equals(tmpNode.getSuperId())) {
                    tmpList.add(findChildren(tmpNode));
                }
            }
        }
        node.setChildren(tmpList);
        return node;
    }
}
