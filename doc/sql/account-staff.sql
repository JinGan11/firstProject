/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : test2

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2019-07-26 11:05:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `account_id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `account_name` varchar(15) character set utf8 NOT NULL COMMENT '登录账号',
  `account_password` varchar(50) character set utf8 NOT NULL COMMENT '登录密码',
  `staff_id` bigint(20) NOT NULL default '0' COMMENT '外键，与staff表关联,0表示不关联员工',
  `account_secret_email` varchar(100) character set utf8 NOT NULL default '' COMMENT '密保邮箱',
  `account_create_time` datetime NOT NULL COMMENT '新建时间',
  `account_creator_id` bigint(20) NOT NULL COMMENT '新建人ID',
  `account_update_time` datetime NOT NULL COMMENT '修改时间，默认=新建时间',
  `account_updater_id` bigint(20) NOT NULL COMMENT '修改人ID',
  `account_state` enum('无效','冻结','正常') character set utf8 NOT NULL default '正常' COMMENT '账号的状态',
  `remarks` varchar(200) character set utf8 NOT NULL default '' COMMENT '备注',
  PRIMARY KEY  (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_account
-- ----------------------------

-- ----------------------------
-- Table structure for `t_history_secret`
-- ----------------------------
DROP TABLE IF EXISTS `t_history_secret`;
CREATE TABLE `t_history_secret` (
  `history_id` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `account_id` bigint(20) NOT NULL COMMENT '关联t_account表',
  `staff_id` bigint(20) NOT NULL COMMENT '关联staff表',
  `history_account_password` varchar(50) character set utf8 NOT NULL COMMENT '账号使用过的的密码',
  `history_create_time` datetime NOT NULL COMMENT '修改密码的时间',
  `history_creator_id` bigint(20) NOT NULL COMMENT '修改密码者account_id',
  `history_update_time` datetime NOT NULL,
  `history_updater_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_history_secret
-- ----------------------------

-- ----------------------------
-- Table structure for `t_staff`
-- ----------------------------
DROP TABLE IF EXISTS `t_staff`;
CREATE TABLE `t_staff` (
  `staff_id` bigint(20) NOT NULL COMMENT '主键',
  `satff_num` varchar(20) NOT NULL COMMENT '员工编号',
  `staff_name` varchar(30) NOT NULL COMMENT '员工姓名',
  `staff_sex` enum('','女','男') NOT NULL default '' COMMENT '性别',
  `staff_telephone` char(11) NOT NULL COMMENT '手机号码',
  `staff_email` varchar(100) NOT NULL default '' COMMENT '邮箱地址',
  `department_id` bigint(20) NOT NULL COMMENT '所属部门id',
  `staff_is_quit` enum('0','1') NOT NULL default '0' COMMENT '是否离职 0-在职 1-离职',
  `staff_create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` bigint(20) NOT NULL COMMENT '新建人id',
  `staff_update_time` datetime NOT NULL COMMENT '修改时间，新建时，默认=新建时间',
  `updater_id` bigint(20) NOT NULL COMMENT '修改人id',
  `remarks` varchar(200) NOT NULL default '' COMMENT '备注',
  PRIMARY KEY  (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_staff
-- ----------------------------
