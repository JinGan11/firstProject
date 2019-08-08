package com.ucar.combination.utils;

import com.ucar.combination.model.dto.DepartmentUpperDto;

import java.util.ArrayList;
import java.util.List;

/**
 * description:构建部门树结构，带支持业务线
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/8/8 8:58
 **/
public class DepartmentUpperTreeBuilder {

    // 根节点的编号
    private static final String rootDepartmentNo = "Z000001";

    private List<DepartmentUpperDto> list;
    private DepartmentUpperDto rootNode;
    private Long id; // 选中的部门的id

    public DepartmentUpperDto buildTree(List<DepartmentUpperDto> treeDtos, Long chooseId) {
        list = treeDtos;
        id = chooseId;
        findRoot();
        findChildren(rootNode, false);
        return rootNode;
    }

    // 找根节点
    private void findRoot() {
        for (DepartmentUpperDto node : list) {
            if (rootDepartmentNo.equals(node.getDepartmentNo())) {
                rootNode = node;
                break;
            }
        }
    }

    /**
     * description: 查找节点的子节点，如果是选中id及其子节点，则设置canChoose为false
     *
     * @return
     * @author 郑开添（kaitian.zheng@ucarinc.com）
     * @date 2019/8/8 9:30
     * @params node节点，isTargetsChildren是否为选中id的子节点
     */
    private DepartmentUpperDto findChildren(DepartmentUpperDto node, Boolean isTargetsChildren) {
        // 当前节点为选中节点或其下属节点
        if (isTargetsChildren || node.getId().equals(id)) {
            node.setCanChoose(false);
            isTargetsChildren = true;
        }
        // 查找子节点
        List<DepartmentUpperDto> tmpList = new ArrayList<>();
        for (DepartmentUpperDto tmpNode : list) {
            if (node.getDepartmentNo().equals(tmpNode.getUpperDepartmentNo())) {
                tmpList.add(findChildren(tmpNode, isTargetsChildren));
            }
        }
        node.setChildren(tmpList);
        return node;
    }
}
