/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : ucar_combination

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2019-07-26 11:02:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_role_info
-- ----------------------------
DROP TABLE IF EXISTS `t_role_info`;
CREATE TABLE `t_role_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `business_line` tinyint(3) DEFAULT NULL COMMENT '业务线',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_emp` bigint(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_emp` bigint(11) DEFAULT NULL COMMENT '修改人id',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态',
  `approver_emp` bigint(11) DEFAULT NULL COMMENT '审批人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_info
-- ----------------------------
