/*
Navicat MySQL Data Transfer

Source Server         : 第一次虚拟项目
Source Server Version : 50624
Source Host           : 10.104.20.123:3306
Source Database       : ucar_combination

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-07-27 16:09:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_r_account_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_r_account_role`;
CREATE TABLE `t_r_account_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT ' 主键id',
  `account_id` bigint(20) NOT NULL COMMENT '账号id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `create_emp` bigint(20) NOT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_emp` bigint(20) NOT NULL COMMENT '修改人id',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_r_account_role
-- ----------------------------
