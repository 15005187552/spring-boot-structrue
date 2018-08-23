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

 Date: 08/23/2018 11:21:22 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_member`
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `ID` bigint(20) NOT NULL COMMENT '会员自增ID',
  `REG_MOBILE` varchar(255) DEFAULT NULL COMMENT '注册手机号',
  `AVATAR` varchar(255) DEFAULT NULL COMMENT '头像路径',
  `NICK_NAME` varchar(255) DEFAULT NULL COMMENT '会员昵称',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';

SET FOREIGN_KEY_CHECKS = 1;
