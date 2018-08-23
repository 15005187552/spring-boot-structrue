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

 Date: 08/23/2018 16:11:39 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_member_password`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_password`;
CREATE TABLE `t_member_password` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '密码ID',
  `PASSWORD` varchar(255) NOT NULL COMMENT '密码值',
  `SALT` varchar(255) NOT NULL COMMENT '加密盐值',
  `LAST_MODIFY_TIME` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号密码表';

SET FOREIGN_KEY_CHECKS = 1;
