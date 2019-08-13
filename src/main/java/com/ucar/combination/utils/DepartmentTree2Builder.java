package com.ucar.combination.utils;

import com.ucar.combination.model.dto.DepartmentTree2Dto;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/8/12 17:14
 **/
public class DepartmentTree2Builder {

    // 根节点的编号
    private static final String rootDepartmentNo = "Z000001";

    private List<DepartmentTree2Dto> list; // 部门列表
    private DepartmentTree2Dto rootNode; // 根节点
    private Long checkedId; //选中的id

    // 获取结果
    public DepartmentTree2Dto getRootNode() {
        return rootNode;
    }

    // 构建树，不含删除
    public DepartmentTree2Builder(List<DepartmentTree2Dto> list) {
        this.list = list;
        initList();
        findRootNode();
        findChildrenWithoutDeleted(rootNode);
    }

    // 构建树，含删除
    public DepartmentTree2Builder(List<DepartmentTree2Dto> list, Boolean withDeleted) {
        this.list = list;
        initList();
        findRootNode();
        findChildren(rootNode);
    }

    // 构建树，含删除、修改上级部门用
    public DepartmentTree2Builder(List<DepartmentTree2Dto> list, Long checkedId) {
        this.list = list;
        this.checkedId = checkedId;
        initList();
        this.list = SupportBusiness2Util.setCanChooseBySupports(this.list, this.checkedId);
        findRootNode();
        findChildrenForUpper(rootNode, false);
    }


    // 设置初始值
    private void initList() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCanChoose(true);
            list.get(i).setNodeIsLeaf(false);
        }
    }

    // 查找根节点
    private void findRootNode() {
        for (DepartmentTree2Dto node : list) {
            if (rootDepartmentNo.equals(node.getDepartmentNo())) {
                rootNode = node;
                break;
            }
        }
    }

    // 递归查找子节点，不包含删除的
    private DepartmentTree2Dto findChildrenWithoutDeleted(DepartmentTree2Dto node) {
        List<DepartmentTree2Dto> tmpList = new ArrayList<>();
        for (DepartmentTree2Dto tmpNode : list) {
            if (node.getDepartmentNo().equals(tmpNode.getUpperDepartmentNo()) && tmpNode.getStatus() == 1) {
                tmpList.add(findChildrenWithoutDeleted(tmpNode));
            }
        }
        node.setChildren(tmpList);
        if (node.getChildren().size() <= 0) node.setNodeIsLeaf(true); // 设置叶子节点标识
        return node;
    }

    // 递归查找子节点，包含删除
    private DepartmentTree2Dto findChildren(DepartmentTree2Dto node) {
        List<DepartmentTree2Dto> tmpList = new ArrayList<>();
        for (DepartmentTree2Dto tmpNode : list) {
            if (node.getDepartmentNo().equals(tmpNode.getUpperDepartmentNo())) {
                tmpList.add(findChildren(tmpNode));
            }
        }
        node.setChildren(tmpList);
        if (node.getChildren().size() <= 0) node.setNodeIsLeaf(true); // 设置叶子节点标识
        return node;
    }

    // 递归查找子节点，用于修改上级部门
    private DepartmentTree2Dto findChildrenForUpper(DepartmentTree2Dto node, Boolean isTargetsChildren) {
        // 当前节点为选中节点或其下属节点
        if (isTargetsChildren || node.getId().equals(checkedId)) {
            node.setCanChoose(false);
            isTargetsChildren = true;
        }
        List<DepartmentTree2Dto> tmpList = new ArrayList<>();
        for (DepartmentTree2Dto tmpNode : list) {
            if (node.getDepartmentNo().equals(tmpNode.getUpperDepartmentNo())) {
                tmpList.add(findChildrenForUpper(tmpNode, isTargetsChildren));
            }
        }
        node.setChildren(tmpList);
        if (node.getChildren().size() <= 0) node.setNodeIsLeaf(true); // 设置叶子节点标识
        return node;
    }


}
