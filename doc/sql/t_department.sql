/*
Navicat MySQL Data Transfer

Source Server         : 公司的数据库
Source Server Version : 50624
Source Host           : 10.104.20.123:3306
Source Database       : ucar_combination

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-07-26 15:24:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_department`
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `create_emp` varchar(60) NOT NULL COMMENT '创建的员工，格式：登录账号（员工姓名）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_emp` varchar(60) NOT NULL COMMENT '修改的员工，格式：登录账号（员工姓名）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注栏',
  `department_no` char(7) NOT NULL COMMENT '部门编号',
  `workplace` smallint(5) unsigned DEFAULT NULL COMMENT '办公点标识，三位数字，同一父节点下需唯一，不同父节点下可重复。',
  `department_name` varchar(40) NOT NULL COMMENT '部门名称',
  `staff_num` varchar(20) DEFAULT NULL COMMENT '负责人id，对应员工表的员工编号',
  `staff_name` varchar(30) DEFAULT NULL COMMENT '负责人姓名，对应员工表的员工姓名',
  `city_no` int(10) unsigned NOT NULL COMMENT '所在城市，对应城市的城市编号',
  `address` varchar(255) DEFAULT NULL COMMENT '部门地址，当部门级别为办公点时为必填项，当部门级别非办公点时非必填。',
  `telephone` varchar(11) NOT NULL COMMENT '联系手机号',
  `email` varchar(30) DEFAULT NULL COMMENT '联系的邮箱',
  `landline` varchar(12) DEFAULT NULL COMMENT '座机号',
  `level` enum('办公点','区域','管理部','分公司','总部') NOT NULL COMMENT '部门级别',
  `upper_department_no` char(7) NOT NULL COMMENT '上级部门编号，对应本表的部门编号',
  `support_business` varchar(100) NOT NULL COMMENT '支持业务线，多选，存储格式“租车&专车&买买车”。',
  `department_type` enum('门店','停车场','交车中心','维修厂') DEFAULT NULL COMMENT '部门类型，当部门级别为办公点时必填',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '数据有效性，1时有效，0时无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_department
-- ----------------------------
