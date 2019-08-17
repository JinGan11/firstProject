package com.ucar.combination.model.dto;

import com.ucar.combination.model.Department;

/**
 * @program: combination
 * @description: 部门查找的数据传输BEAN
 * @author: jing.luo01@ucarinc.com
 * @create: 2019-08-06 17:03
 */
public class DepartmentDto extends Department {
	private String cityName;
   	private String createEmpName;
   	private String modifyEmpName;

	public String getCreateEmpName() {
		return createEmpName;
	}

	public void setCreateEmpName(String createEmpName) {
		this.createEmpName = createEmpName;
	}

	public String getModifyEmpName() {
		return modifyEmpName;
	}

	public void setModifyEmpName(String modifyEmpName) {
		this.modifyEmpName = modifyEmpName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}

