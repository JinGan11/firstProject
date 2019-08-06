package com.ucar.combination.dao;

import com.ucar.combination.model.Department;
import com.ucar.combination.model.dto.DepartmentTreeDto;
import com.ucar.combination.model.dto.SearchDepartmentDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author 郑开添（kaitian.zheng@ucarinc.com）
 * @version 1.0
 * @date 2019/7/30 10:20
 **/
@Repository(value = "DepartmentDao")
public interface DepartmentDao {

	/**
	 * description: 查找所有有效的部门
	 * @author 郑开添（kaitian.zheng@ucarinc.com）
	 * @date 2019/8/6 17:05
	 * @params
	 * @return 存放所有有效部门的DepartmentDto的list
	 */
	List<DepartmentTreeDto> queryDepartmentTreeAll();

	/**
	 * description: 插入一个新的部门，创建/修改时间为当前时间
	 * @author 郑开添（kaitian.zheng@ucarinc.com）
	 * @date 2019/8/6 17:05
	 * @params
	 * @return
	 */
	void insertDepartment(Department department);

	/**
	 * description: 删除部门（逻辑删除）
	 * @author 郑开添（kaitian.zheng@ucarinc.com）
	 * @date 2019/8/6 17:06
	 * @params
	 * @return
	 */
	void deleteDepartment(Long id);

	/*
	 * description:
	 * @author jing.luo01@ucarinc.com
	 * @date   2019/8/2 11:46
	 * @params
	 * @return 查询出来的SearchDepartmentDto结果，在
	 */
	List<SearchDepartmentDto> searchDepartment(Map<String, Object> map);

	/*
	 * description:
	 * @author jing.luo01@ucarinc.com
	 * @date   2019/8/5 16:53
	 * @params id 传入一个LONG型的ID进行查找
	 * @return Department返回一个部门类。
	 */
	Department getDepartmentById(Long id);
}
