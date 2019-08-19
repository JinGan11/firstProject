package com.ucar.combination.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ucar.combination.common.QueryParam;
import com.ucar.combination.common.ResultPage;
import com.ucar.combination.dao.AccountManageDao;
import com.ucar.combination.dao.DepartmentDao;
import com.ucar.combination.dao.EmployeeManageDao;
import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.*;
import com.ucar.combination.service.DepartmentService;
import com.ucar.combination.utils.DepartmentTree2Builder;
import com.ucar.combination.utils.DepartmentTreeBuilder;
import com.ucar.combination.utils.DepartmentUpperTreeBuilder;
import com.ucar.combination.utils.SupportBusinessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * description:部门管理
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/7/30 10:16
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    DepartmentService departmentService;
    @Autowired(required = false)
    private AccountManageDao accountManageDao;
    @Autowired(required = false)
    private EmployeeManageDao employeeManageDao;

    @Override
    public DepartmentTreeDto buildTree() {
        List<DepartmentTreeDto> list = departmentDao.queryDepartmentTreeAll();
        return new DepartmentTreeBuilder().buildTree(list);
    }

    @Override
    public void insertDepartment(Department department) {
        departmentDao.insertDepartment(department);
    }

    @Override
    public Map<String, Object> deleteDepartment(Long departmentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        String no = departmentDao.selectDepartmentNoById(departmentId);
        if (no == null) {
            map.put("result", false);
            map.put("msg", "未知错误！");
            return map;
        }
        if (departmentDao.checkLowerDepartmentsStatus(no) > 0) {
            map.put("result", false);
            map.put("msg", "删除失败，下级部门存在有效部门！");
            return map;
        }
        departmentDao.deleteDepartment(departmentId);
        return map;
    }

    @Override
    public DepartmentUpperDto buildUpperTree(Long choosedId) {
        List<DepartmentUpperDto> list = departmentDao.queryDepartmentUpperAll();
        list = SupportBusinessUtil.setCanChooseBySupports(list, choosedId);
        DepartmentUpperDto rootNode = new DepartmentUpperTreeBuilder().buildTree(list, choosedId);
        return rootNode;
    }

    @Override
    public Map<String, Object> updateUpperDepartment(Long id, String upperDepartmentNo) {
        Map<String, Object> map = new HashMap<>();
        // 办公点校验
        Map<String, Object> map2 = departmentService.checkWorkplaceForUpper(id, upperDepartmentNo);
        map.putAll(map2);
        // 状态校验
        if (!departmentService.checkStatusChange(id, upperDepartmentNo)) {
            map.put("result", false);
            String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
            map.put("msg", tmp + "部门状态发生改变！");
            return map;
        }
        // 该上级部门在自己下方
        List<DepartmentTree2Dto> listDto = departmentDao.selectDepartmentTree2Dto();
        DepartmentTree2Builder builder = new DepartmentTree2Builder(listDto);
        if(!builder.checkUpperLegal(id,upperDepartmentNo)){
            map.put("result", false);
            String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
            map.put("msg", tmp + "上级部门发生改变！");
            return map;
        }
        // 业务线校验
        String upperSupport = departmentDao.selectUpperDepartmentBusiness(upperDepartmentNo);
        String support = departmentDao.selectSupportBusinessByNo(departmentDao.selectDepartmentNoById(id));
        if(support==null || upperSupport==null || SupportBusinessUtil.compareSups(upperSupport,support)<0){
            map.put("result", false);
            String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
            map.put("msg", tmp + "上级部门支持业务线发生更改！");
        }
        // 更新
        if ((Boolean) map.get("result")) {
            departmentDao.updateUpperDepartment(id, upperDepartmentNo);
        }
        return map;
    }

    @Override
    public String selectSupportBusiness(String departmentNo) {
        return departmentDao.selectSupportBusinessByNo(departmentNo);
    }

    @Override
    public DepartmentEditDto selectDepartmentForEdit(Long id) {
        DepartmentEditDto editDto = departmentDao.selectDepartmentForEdit(id);
        String staffCreate = departmentDao.selectEmployeeNameByAccountId(editDto.getCreateEmp());
        String staffModify = departmentDao.selectEmployeeNameByAccountId(editDto.getModifyEmp());
        if (staffCreate != null) editDto.setCreateName(editDto.getCreateName() + "（" + staffCreate + "）");
        if (staffModify != null) editDto.setModifyName(editDto.getModifyName() + "（" + staffModify + "）");
        return editDto;
    }

    @Override
    public List<Long> selectDataPowerIds(Long accountId) {
        // type:1-全部,2-递归,3-本部门,4-本人,5-手动选择
        Integer powerType = accountManageDao.selectPermissionsById(accountId);
        if (powerType == null) return null;
        switch (powerType) {
            case 1: // 1-全部（不建议在数据权限为1-全部时使用，效率低）
                return departmentDao.selectDepartmentIdAll();
            case 2: // 2-递归
                DepartmentTreeBuilder builder = new DepartmentTreeBuilder();
                List<DepartmentTreeDto> list = departmentDao.queryDepartmentTreeAll();
                builder.buildTree(list);
                List<Long> ids2 = builder.getRecursionId(employeeManageDao.selectDepartmentId(accountId));
                return ids2;
            case 3: //3-本部门
                List<Long> ids3 = new ArrayList<>();
                ids3.add(employeeManageDao.selectDepartmentId(accountId));
                return ids3;
            case 4: // 4-本人（相当于没有权限，但是应该可以操作自己？）
                return new ArrayList<Long>();
            case 5: // 5-手动选择
                return departmentDao.selectDataPowerChoosed(accountId);
            default:
                return null;
        }
    }

    @Override
    public Map<String, Object> checkInput(Department department) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        if (departmentDao.checkDepartmentNo(department) > 0) {
            map.put("result", false);
            map.put("msg", "部门编号已存在！");
            return map;
        }
        if (department.getLevel().equals(5)) {
            if (department.getWorkplace().equals(0)) {
                map.put("result", false);
                String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
                map.put("msg", tmp + "办公点标识不能设置为0！");
            } else if (departmentDao.checkWorkplace(department) > 0) {
                map.put("result", false);
                String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
                map.put("msg", tmp + "办公点标识已存在！");
            }
        }

        /*
        //由于已经无法修改状态了，所以这两行没用了
        if (departmentDao.checkUpperDepartment(department) < 1 && !department.getDepartmentNo().equals("Z000001")) {
            map.put("result", false);
            String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
            map.put("msg", tmp + "上级部门状态为无效，无法将当前部门改为有效！");
        }
        if (department.getStatus().equals(0) && (departmentDao.checkLowerDepartment(department) > 0)) {
            map.put("result", false);
            String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
            map.put("msg", tmp + "下级部门包含状态为有效的部门，无法将当前部门改为无效！");
        }
        */

        // 业务线判断
        List<String> supports = departmentDao.selectLowerDepartmentBusiness(department.getDepartmentNo());
        if (supports != null && supports.size() > 0) {
            if (!SupportBusinessUtil.compareSups(department.getSupportBusiness(), supports)) {
                map.put("result", false);
                String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
                map.put("msg", tmp + "下级部门的业务线不能拥有本部门没有的，请修改或删除下级部门！");
            }
        }
        String support = departmentDao.selectUpperDepartmentBusiness(department.getUpperDepartmentNo());
        if (support == null || SupportBusinessUtil.compareSups(support, department.getSupportBusiness()) < 0) {
            map.put("result", false);
            String tmp = (String) map.get("msg") == null ? "" : (String) map.get("msg");
            map.put("msg", tmp + "上级部门的业务线发生更改，你选中的业务线中含有上级部门已取消的！");
        }
        return map;
    }

    @Override
    public Boolean checkStatusChange(List<Long> departmentId, List<String> departmentNo) {
        if (departmentId != null)
            for (Long tmpId : departmentId)
                if (departmentDao.checkStatusById(tmpId) < 1) return false;
        if (departmentNo != null)
            for (String tmpNo : departmentNo)
                if (departmentDao.checkStatusByNo(tmpNo) < 1) return false;
        return true;
    }

    @Override
    public Boolean checkStatusChange(Long departmentId, String departmentNo) {
        if (departmentId != null)
            if (departmentDao.checkStatusById(departmentId) < 1) return false;
        if (departmentNo != null)
            if (departmentDao.checkStatusByNo(departmentNo) < 1) return false;
        return true;
    }

    @Override
    public Map<String, Object> checkWorkplaceForUpper(Long id, String upperDepartment) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        if (departmentDao.selectLevel(id).equals(5)) {
            if (departmentDao.checkWorkplaceForUpper(id, upperDepartment) > 0) {
                map.put("result", false);
                map.put("msg", "该上级部门中已有办公点标识与本部门相同的部门，请修改办公点标识后再进行修改！");
            }
        }
        return map;
    }

    @Override
    public DepartmentTree2Dto buildTree2() {
        List<DepartmentTree2Dto> list = departmentDao.selectDepartmentTree2Dto();
        DepartmentTree2Builder builder = new DepartmentTree2Builder(list);
        return builder.getRootNode();
    }

    /*
     * description:
     * @author jing.luo01@ucarinc.com
     * @date   2019/8/2 11:42
     * @params queryParam 输入查询分页的QueryParam
     * @return 返回查询部门
     */
    @Override
    public ResultPage searchDepartment(QueryParam queryParam) {
        Page<?> page = PageHelper.startPage(queryParam.getPage(), queryParam.getLimit());
        List<SearchDepartmentDto> list = departmentDao.searchDepartment(queryParam);
        return new ResultPage(list, (int) page.getTotal(), queryParam.getLimit(), queryParam.getPage());
    }

    public Boolean updateDepartment(Department department) {
        departmentDao.updateDepartment(department);
        return true;
    }

    /**
     * description: 访问DAO层得到DEPARTMENT数据库的信息
     *
     * @return
     * @author jing.luo01@ucarinc.com
     * @date 2019/8/6 10:13
     * @params id 描述
     */
    @Override
    public DepartmentDto getDepartmentDtoById(String id) {
        return departmentDao.getDepartmentDtoById(Long.valueOf(id));
    }

    /**
     * description: 得到所有的部门信息
     *
     * @return 返回部门信息的LIST集合
     * @author jing.luo01@ucarinc.com
     * @date 2019/8/15 9:44
     * @params queryParam 描述
     */
    @Override
    public List<SearchDepartmentDto> searchDepartmentAll(QueryParam queryParam) {
        return departmentDao.searchDepartment(queryParam);
    }
}
