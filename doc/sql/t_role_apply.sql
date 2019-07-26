/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : ucar_combination

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2019-07-26 11:02:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_role_apply
-- ----------------------------
DROP TABLE IF EXISTS `t_role_apply`;
CREATE TABLE `t_role_apply` (
  `id` bigint(11) NOT NULL COMMENT '申请编号',
  `role_id` bigint(11) DEFAULT NULL COMMENT '角色id',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态',
  `apply_id` bigint(11) DEFAULT NULL COMMENT '申请人id',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `modify_emp` bigint(11) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_apply
-- ----------------------------
