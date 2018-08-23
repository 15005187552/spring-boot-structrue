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

 Date: 08/23/2018 16:13:19 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_provider_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_provider_user`;
CREATE TABLE `t_provider_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROVIDER_ID` int(11) NOT NULL COMMENT '所属服务商ID',
  `MEMBER_ID` bigint(20) NOT NULL COMMENT '服务商关联会员ID',
  `ROLES_CODE` smallint(6) NOT NULL DEFAULT '0' COMMENT '角色代码',
  `CREATE_TIME` datetime NOT NULL COMMENT '加入时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务商员工表';

SET FOREIGN_KEY_CHECKS = 1;
