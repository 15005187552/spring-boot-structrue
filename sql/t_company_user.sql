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

 Date: 08/23/2018 13:40:51 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_company_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_company_user`;
CREATE TABLE `t_company_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMPANY_ID` int(11) NOT NULL COMMENT '所属企业ID',
  `MEMBER_ID` bigint(20) NOT NULL COMMENT '会员ID',
  `ROLES_CODE` smallint(6) NOT NULL DEFAULT '0' COMMENT '角色代码',
  `CREATE_TIME` datetime NOT NULL COMMENT '加入时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否离职',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业员工表';

SET FOREIGN_KEY_CHECKS = 1;
