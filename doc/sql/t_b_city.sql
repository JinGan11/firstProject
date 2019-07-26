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

 Date: 26/07/2019 15:19:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_b_city
-- ----------------------------
DROP TABLE IF EXISTS `t_b_city`;
CREATE TABLE `t_b_city` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID ,自增',
  `create_emp` varchar(60) NOT NULL COMMENT '创建者，存储内容：登陆账号（员工姓名）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_emp` varchar(60) NOT NULL COMMENT '修改者',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `city_no` int(10) unsigned NOT NULL COMMENT '编号',
  `international_code` varchar(25) NOT NULL COMMENT '国际代码',
  `area_code` varchar(20) NOT NULL COMMENT '区号',
  `city_name` varchar(50) NOT NULL COMMENT '名称',
  `upper_city_no` int(10) unsigned NOT NULL COMMENT '上级地区编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
