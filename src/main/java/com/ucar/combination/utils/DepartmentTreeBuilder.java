package com.ucar.combination.utils;

import java.util.ArrayList;
import java.util.List;

import com.ucar.combination.model.dto.DepartmentTreeDto;

/**
 * description:构建树结构（存在问题：建树方法应该写为构造...后续改）
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

    // 建立树，返回根节点
    public DepartmentTreeDto buildTree(List<DepartmentTreeDto> treeDtos) {
        list = treeDtos;
        findRoot();
        findChildren(rootNode);
        return rootNode;
    }

    // 获取当前部门及子部门的id，需要先建立树
    public List<Long> getRecursionId(Long id) {
        if (list == null) return null;
        DepartmentTreeDto node = findCurrentNode(id);
        List<Long> ids = findRecursionId(node);
        return ids;
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

    // 寻找当前部门节点
    private DepartmentTreeDto findCurrentNode(Long id) {
        for (DepartmentTreeDto node : list) {
            if (id.equals(node.getId())) {
                return node;
            }
        }
        return null;
    }

    // 递归找自身及下级部门的id
    private List<Long> findRecursionId(DepartmentTreeDto node) {
        List<Long> ids = new ArrayList<>();
        ids.add(node.getId());
        for (int i = 0; i < node.getChildren().size(); i++) {
            ids.addAll(findRecursionId(node.getChildren().get(i)));
        }
        return ids;
    }

}
