/*
Navicat MySQL Data Transfer

Source Server         : 第一次虚拟项目
Source Server Version : 50624
Source Host           : 10.104.20.123:3306
Source Database       : ucar_combination

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-07-27 17:10:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_power`
-- ----------------------------
DROP TABLE IF EXISTS `t_power`;
CREATE TABLE `t_power` (
  `power_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `power_name` varchar(200) NOT NULL COMMENT '类似于文件路径形式存取一个模块权限，注意长度限制',
  PRIMARY KEY (`power_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_power
-- ----------------------------
