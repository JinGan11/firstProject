/*
Navicat MySQL Data Transfer

Source Server         : ucar_combination
Source Server Version : 50624
Source Host           : 10.104.20.123:3306
Source Database       : ucar_combination

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-07-26 15:31:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_company
-- ----------------------------
DROP TABLE IF EXISTS `t_company`;
CREATE TABLE `t_company` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `create_emp` varchar(60) NOT NULL COMMENT '存储内容：登录账户（员工姓名）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_emp` varchar(60) NOT NULL COMMENT '修改该行的员工',
  `modify_time` datetime NOT NULL COMMENT '最后修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `company_no` bigint(20) unsigned NOT NULL COMMENT '公司编号',
  `company_name` varchar(60) NOT NULL COMMENT '公司名称',
  `credit_code` varchar(18) NOT NULL COMMENT '统一社会信用代码，15或18位数字或者字符',
  `company_type` enum('有限责任公司','股份有限公司') DEFAULT NULL COMMENT '公司类型',
  `business_deadline` date DEFAULT NULL COMMENT '营业期限',
  `company_address` varchar(100) DEFAULT NULL COMMENT '公司地址',
  `business_scope` varchar(200) DEFAULT NULL COMMENT '经营范围',
  `legal_person` varchar(60) DEFAULT NULL COMMENT '法人代表',
  `registered_capital` bigint(20) unsigned DEFAULT NULL COMMENT '注册资本，1-20位数字，单位元',
  `establish_time` date DEFAULT NULL COMMENT '成立日期',
  `registered_institution` varchar(60) DEFAULT NULL COMMENT '登记机关',
  `issue_date` date DEFAULT NULL COMMENT '核准日期',
  `registered_status` varchar(60) DEFAULT NULL COMMENT '登记状态',
  `liscense_path` varchar(4096) DEFAULT NULL COMMENT '存多张图片文件链接，分隔符隔开（未定使用哪个分隔符）',
  `company_nature` enum('一般纳税人','小规模纳税人') DEFAULT NULL COMMENT '公司性质',
  `registration_address` varchar(100) DEFAULT NULL COMMENT '注册地址',
  `bank_name` varchar(60) DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(20) DEFAULT NULL COMMENT '开户银行账号',
  `telephone` varchar(12) DEFAULT NULL COMMENT '注册电话',
  `company_mark` enum('总公司','子公司') DEFAULT NULL COMMENT '总公司标志，两个选项',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
