/*
 Navicat Premium Data Transfer

 Source Server         : mac_mysql
 Source Server Type    : MySQL
 Source Server Version : 50621
 Source Host           : 127.0.0.1
 Source Database       : geckodb

 Target Server Type    : MySQL
 Target Server Version : 50621
 File Encoding         : utf-8

 Date: 08/23/2018 16:11:22 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_member_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_account`;
CREATE TABLE `t_member_account` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账号ID',
  `USERNAME` varchar(255) NOT NULL COMMENT '账号ID',
  `TYPE` int(2) NOT NULL COMMENT '账号类型',
  `MEMBER_ID` bigint(20) NOT NULL COMMENT '所属会员ID',
  `PASSWORD_ID` bigint(20) NOT NULL COMMENT '关联密码ID',
  `EXT_INFO` longtext COMMENT '账号扩展信息',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员账号表';

SET FOREIGN_KEY_CHECKS = 1;
