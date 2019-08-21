package com.ucar.combination.common;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:公共枚举
 *
 * @author qingyu.lan@ucarinc.com
 * @version 1.0
 * @date: 2019-07-30 08:46:27
 **/
public abstract class CommonEnums {
	public static interface IEnum {
		int getI();

		String getN();
	}


	/**
	 * description:性别枚举
	 *
	 * @author qingyu.lan@ucarinc.com
	 * @version 1.0
	 * @date: 2019-07-30
	 **/
	public static enum Sex implements IEnum {
		MALE(1, "男"),
		FEMALE(2, "女");
		private final int i;
		private final String n;

		Sex(int i, String n) {
			this.i = i;
			this.n = n;
		}

		@Override
		public String getN() {
			return n;
		}

		@Override
		public int getI() {
			return i;
		}

		public static Sex valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (Sex item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}
	}


	/**
	 * description:数据权限类型枚举
	 *
	 * @author junqiang.zhang@ucarinc.com
	 * @version 1.0
	 * @date: 2019-07-30
	 **/
	public static enum Permission implements IEnum {
		ALL(1, "全部"),
		RECURSION(2, "递归"),
		DEPARMENT(3, "本部门"),
		SELF(4, "本人"),
		CHOSSE(5, "手动选择");
		private final int i;
		private final String n;

		Permission(int i, String n) {
			this.i = i;
			this.n = n;
		}

		@Override
		public String getN() {
			return n;
		}

		@Override
		public int getI() {
			return i;
		}

		public static Permission valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (Permission item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}
	}

	/**
	 * description:是否在职枚举
	 *
	 * @author qingyu.lan@ucarinc.com
	 * @version 1.0
	 * @date: 2019-07-30
	 **/
	public static enum isDimission implements IEnum {
		INSERVICE(0, "在职"),
		LEAVE(1, "离职");
		private final int i;
		private final String n;

		isDimission(int i, String n) {
			this.i = i;
			this.n = n;
		}

		@Override
		public String getN() {
			return n;
		}

		@Override
		public int getI() {
			return i;
		}

		public static isDimission valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (isDimission item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}
	}

	/**
	 * description:账户状态枚举
	 *
	 * @author qingyu.lan@ucarinc.com
	 * @version 1.0
	 * @date: 2019-07-30
	 **/
	public static enum AccountStatusEnum implements IEnum {
		All(0,"全部"),
		NORMAl(1, "正常"),
		FRUZEN(2, "冻结"),
		INVALID(3, "无效");
		private final int i;
		private final String n;

		AccountStatusEnum(int i, String n) {
			this.i = i;
			this.n = n;
		}

		@Override
		public String getN() {
			return n;
		}

		@Override
		public int getI() {
			return i;
		}

		public static AccountStatusEnum valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (AccountStatusEnum item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}
	}

	/**
	 * description:公司类型枚举
	 *
	 * @author jianan.shu@ucarinc.com
	 * @version 1.0
	 * @date: 2019/8/3 10:34
	 */
	public static enum CompanyType implements IEnum {
		LIABILITY(1, "有限责任公司"),
		STOCK(2, "股份有限公司");
		private final int i;
		private final String n;

		CompanyType(int i, String n) {
			this.i = i;
			this.n = n;
		}

		@Override
		public int getI() {
			return i;
		}

		@Override
		public String getN() {
			return n;
		}

		public static CompanyType valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (CompanyType item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}
	}

	/**
	 * description:公司性质枚举
	 *
	 * @author jianan.shu@ucarinc.com
	 * @version 1.0
	 * @date: 2019/8/3 10:34
	 */
	public static enum CompanyNature implements IEnum {
		ORDINARY(1, "一般纳税人"),
		SMALL(2, "小规模纳税人");
		private final int i;
		private final String n;

		CompanyNature(int i, String n) {
			this.i = i;
			this.n = n;
		}

		@Override
		public int getI() {
			return i;
		}

		@Override
		public String getN() {
			return n;
		}

		public static CompanyNature valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (CompanyNature item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}
	}

	/**
	 * description:公司状态枚举
	 *
	 * @author jianan.shu@ucarinc.com
	 * @version 1.0
	 * @date: 2019/8/3 10:34
	 */
	public static enum CompanyStatus implements IEnum {
		EFFECTIVE(1, "有效"),
		INVALID(2, "无效");
		private final int i;
		private final String n;

		CompanyStatus(int i, String n) {
			this.i = i;
			this.n = n;
		}

		@Override
		public int getI() {
			return i;
		}

		@Override
		public String getN() {
			return n;
		}

		public static CompanyStatus valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (CompanyStatus item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}
	}

	/**
	 * description:总公司标志
	 *
	 * @author jianan.shu@ucarinc.com
	 * @version 1.0
	 * @date: 2019/8/3 11:29
	 */
	public static enum CompanyMark implements IEnum {
		HEADQUARTERS(1, "总公司"),
		SUBSIDIARY(2, "子公司");
		private final int i;
		private final String n;

		CompanyMark(int i, String n) {
			this.i = i;
			this.n = n;
		}

		@Override
		public int getI() {
			return i;
		}

		@Override
		public String getN() {
			return n;
		}

		public static CompanyMark valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (CompanyMark item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}
	}


	/**
	 * @Description: 业务线枚举
	 * @Author min.zhang
	 * @Params
	 * @Return
	 * @Date 9:26 2019/8/3
	 */
	public static enum BusinessLineEnum implements IEnum {
		MMC(1, "买买车"),
		FCAR(2, "闪贷"),
		CAR(3, "租车"),
		UCAR(4, "专车"),
		SAFE(5, "保险");
		private final int i;
		private final String n;

		BusinessLineEnum(int i, String n) {
			this.i = i;
			this.n = n;
		}

		@Override
		public int getI() {
			return i;
		}

		@Override
		public String getN() {
			return n;
		}

		public static BusinessLineEnum valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (BusinessLineEnum item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}
	}

	/**
	 * @Description: 角色申请状态
	 * @Author min.zhang08
	 * @Params
	 * @Return
	 * @Date 9:28 2019/8/3
	 */
	public static enum applyStatusEnum implements IEnum {
		APPLY(1, "已新建"),
		WAIT(2, "待审批"),
		PASS(3, "审批通过"),
		REJECT(4, "审批拒绝"),
		DELETE(5, "已删除");

		private final int i;
		private final String n;

		applyStatusEnum(int i, String n) {
			this.i = i;
			this.n = n;
		}

		@Override
		public int getI() {
			return i;
		}

		@Override
		public String getN() {
			return n;
		}

		public static applyStatusEnum valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (applyStatusEnum item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}

	}

	/*
	 * description: 枚举的IENUM类型，用于将部门类型从数字转换为字符
	 * @author jing.luo01@ucarinc.com
	 * @date   2019/8/6 14:59
	 * @params
	 * @return
	 */
	public static enum DepartmentType implements IEnum {
		STORE(1, "门店"),
		PARKING(2, "停车场"),
		RETURN_CAR(3, "交车中心"),
		MAINTENCE_SHOP(4, "维修厂");

		private final int i;
		private final String n;

		DepartmentType(int i, String n) {
			this.i = i;
			this.n = n;
		}

		@Override
		public int getI() {
			return i;
		}

		@Override
		public String getN() {
			return n;
		}

		public static DepartmentType valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (DepartmentType item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}
	}


	/*
	 * description: 部门级别的枚举类
	 * @author jing.luo01@ucarinc.com
	 * @date   2019/8/6 16:38
	 * @params enumArr 描述

	 * @return
	 */
	public static enum DepartmentLevel implements IEnum{
		OFFICE_SPACE(5,"办公点"),
		REGION(4,"区域"),
		MANAGEMENT(3,"管理部"),
		BRANCH_OFFICE(2,"分公司"),
		HEADQUARTERS(1,"总部");
		private final int i;
		private final String n;

		DepartmentLevel(int i,String n){
			this.i=i;
			this.n=n;
		}

		@Override
		public int getI() {
			return i;
		}

		@Override
		public String getN() {
			return n;
		}
		public static DepartmentLevel valueOf(Integer i){
			if (i==null){
				return null;
			}
			for (DepartmentLevel item:values()){
				if (item.getI()==i){
					return item;
				}
			}
			return null;
		}
		public static String i2n(Integer i,String n){
			IEnum e=valueOf(i);
			if (e==null){
				return n;
			}
			return e.getN();
		}
	}
	public static enum DepartmentStatus implements IEnum{
		EFFECTIVE(1, "有效"),
		INVALID(0, "无效");
		private final int i;
		private final String n;

		DepartmentStatus(int i,String n){
			this.i=i;
			this.n=n;
		}

		@Override
		public int getI() {
			return i;
		}

		@Override
		public String getN() {
			return n;
		}
		public static DepartmentStatus valueOf(Integer i){
			if (i==null){
				return null;
			}
			for (DepartmentStatus item:values()){
				if (item.getI()==i){
					return item;
				}
			}
			return null;
		}
		public static String i2n(Integer i,String n){
			IEnum e=valueOf(i);
			if (e==null){
				return n;
			}
			return e.getN();
		}
	}

	/**
	 * description:角色状态枚举类
	 * @author gan.jin@ucarinc.com
	 * @date 2019/8/7 17:38
	 *
	 */
	public static enum RoleStatusEnum implements IEnum {

		EFFECTIVE(1, "有效"),
		INVALID(0, "无效");
		private final int i;
		private final String n;

		RoleStatusEnum(int i, String n) {
			this.i = i;
			this.n = n;
		}

		@Override
		public int getI() {
			return i;
		}

		@Override
		public String getN() {
			return n;
		}

		public static RoleStatusEnum valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (RoleStatusEnum item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}
	}

	//区域状态
	public static enum RegionStatus implements IEnum{
		EFFECTIVE(1, "有效"),
		INVALID(0, "无效");
		private final int i;
		private final String n;

		RegionStatus(int i, String n) {
			this.i = i;
			this.n = n;
		}

		public static RegionStatus valueOf(Integer i){
			if (i==null){
				return null;
			}
			for (RegionStatus item:values()){
				if (item.getI()==i){
					return item;
				}
			}
			return null;
		}

		@Override
		public int getI() {
			return i;
		}

		@Override
		public String getN() {
			return n;
		}
	}
	public static Map<Integer, String> toEnumMap(IEnum[] enumArr) {
		Map<Integer, String> res = new HashMap<>();
		for (IEnum item : enumArr) {
			res.put(item.getI(), item.getN());
		}
		return res;
	}

	public static List<JSONObject> toJsonList(IEnum[] enumArr) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		for (IEnum item : enumArr) {
			JSONObject js = new JSONObject();
			js.put("value", item.getI());
			js.put("label", item.getN());
			list.add(js);
		}
		return list;
	}
	public static List<JSONObject> permissionsJsonList(IEnum[] enumArr) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jso = new JSONObject();
		jso.put("value",0);
		jso.put("label","全选");
		list.add(jso);
		for (IEnum item : enumArr) {
			JSONObject js = new JSONObject();
			js.put("value", item.getI());
			js.put("label", item.getN());
			list.add(js);
		}
		return list;
	}
	/**
	 * @Description:  角色申请 新建  添加移除 枚举类
	 * @Author: min.zhang08@ucarinc.com
	 * @Params
	 * @Return
	 * @Date  19:36 2019/8/10
	 */
	public static enum applyOperationEnum implements IEnum{

		ADD(1, "添加"),
		REMOVE(2, "移除");

		private final int i;
		private final String n;

		applyOperationEnum(int i, String n) {
			this.i = i;
			this.n = n;
		}


		@Override
		public int getI() {
			return i;
		}

		@Override
		public String getN() {
			return n;
		}

		public static applyOperationEnum valueOf(Integer i) {
			if (i == null) {
				return null;
			}
			for (applyOperationEnum item : values()) {
				if (item.getI() == i) {
					return item;
				}
			}
			return null;
		}

		public static String i2n(Integer i, String def) {
			IEnum e = valueOf(i);
			if (e == null) {
				return def;
			}
			return e.getN();
		}
	}
}
