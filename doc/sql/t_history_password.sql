/*
Navicat MySQL Data Transfer

Source Server         : 第一次虚拟项目
Source Server Version : 50624
Source Host           : 10.104.20.123:3306
Source Database       : ucar_combination

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-07-27 16:08:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_history_password`
-- ----------------------------
DROP TABLE IF EXISTS `t_history_password`;
CREATE TABLE `t_history_password` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '账号密码表主键',
  `account_id` bigint(20) unsigned NOT NULL COMMENT '关联的账号ID',
  `history_password` varchar(50) NOT NULL COMMENT '历史用过的账号密码',
  `create_time` datetime NOT NULL COMMENT '新建时间',
  `create_emp` bigint(20) NOT NULL COMMENT '新建人',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  `modify_emp` bigint(20) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_history_password
-- ----------------------------
