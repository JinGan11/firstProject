/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : ucar_combination

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2019-07-25 15:27:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', 'lan', '123');

-- ----------------------------
-- Table structure for ucar_combination_menu
-- ----------------------------
DROP TABLE IF EXISTS `ucar_combination_menu`;
CREATE TABLE `ucar_combination_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(255) DEFAULT NULL COMMENT '跳转路径',
  `title` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态',
  `super_id` bigint(200) DEFAULT NULL COMMENT '上级节点ID',
  `sort_id` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='系统菜单';

-- ----------------------------
-- Records of ucar_combination_menu
-- ----------------------------
INSERT INTO `ucar_combination_menu` VALUES ('1', '/departmentManagement', '部门管理', 'el-icon-s-promotion', '0', '0', '1');
INSERT INTO `ucar_combination_menu` VALUES ('2', '/employeeManagement', '员工管理', 'el-icon-s-operation\r\n', '0', '0', '2');
INSERT INTO `ucar_combination_menu` VALUES ('3', '/accountManagement', '账号管理', 'el-icon-document', '0', '0', '3');
INSERT INTO `ucar_combination_menu` VALUES ('4', '/roleManagement', '角色管理', 'el-icon-document', '0', '0', '4');
INSERT INTO `ucar_combination_menu` VALUES ('5', '/companyManagement', '公司管理', 'el-icon-s-platform', '0', '0', '5');
INSERT INTO `ucar_combination_menu` VALUES ('6', '/cityInforManagement', '城市信息管理', 'el-icon-coin', '0', '0', '6');
INSERT INTO `ucar_combination_menu` VALUES ('7', '/personalAccountManagement', '个人账户管理', 'el-icon-document', '0', '0', '7');
INSERT INTO `ucar_combination_menu` VALUES ('8', '/search', '查询', 'el-icon-document', '0', '0', '8');
