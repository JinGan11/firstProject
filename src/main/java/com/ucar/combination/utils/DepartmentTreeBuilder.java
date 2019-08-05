package com.ucar.combination.utils;

import java.util.ArrayList;
import java.util.List;

import com.ucar.combination.model.dto.DepartmentTreeDto;

/**
 * description:构建树结构
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/8/5 13:48
 **/
public class DepartmentTreeBuilder {

    // 根节点的编号
    private static final String rootDepartmentNo = "Z000001";

    private List<DepartmentTreeDto> list;
    private DepartmentTreeDto rootNode;

    public DepartmentTreeDto buildTree(List<DepartmentTreeDto> treeDtos) {
        list = treeDtos;
        findRoot();
        findChildren(rootNode);
        return rootNode;
    }

    // 找根节点
    private void findRoot() {
        for (DepartmentTreeDto node : list) {
            if (rootDepartmentNo.equals(node.getDepartmentNo())) {
                rootNode = node;
                break;
            }
        }
    }

    // 递归查找子节点
    private DepartmentTreeDto findChildren(DepartmentTreeDto node) {
        List<DepartmentTreeDto> tmpList = new ArrayList<>();
        for (DepartmentTreeDto tmpNode : list) {
            if (node.getDepartmentNo().equals(tmpNode.getUpperDepartmentNo())) {
                tmpList.add(findChildren(tmpNode));
            }
        }
        node.setChildren(tmpList);
        return node;
    }

}
