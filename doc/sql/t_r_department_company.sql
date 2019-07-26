/*
 Navicat MySQL Data Transfer

 Source Server         : ucar
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : 10.104.20.123:3306
 Source Schema         : ucar_combination

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : 65001

 Date: 26/07/2019 15:19:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_r_department_company
-- ----------------------------
DROP TABLE IF EXISTS `t_r_department_company`;
CREATE TABLE `t_r_department_company` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID ,自增',
  `create_emp` varchar(60) NOT NULL COMMENT '创建者，存储内容：登陆账号（员工姓名）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_emp` varchar(60) NOT NULL COMMENT '修改者',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `department_no` char(7) NOT NULL COMMENT '部门编号：固定首字母+6位字符（共7位），如Z000001\r\n',
  `company_no` bigint(20) unsigned NOT NULL COMMENT '公司编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
