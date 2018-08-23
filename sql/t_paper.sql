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

 Date: 08/23/2018 10:52:30 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_paper`
-- ----------------------------
DROP TABLE IF EXISTS `t_paper`;
CREATE TABLE `t_paper` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL COMMENT '证书名称',
  `TYPE` int(1) DEFAULT NULL COMMENT '适用类型 0: 个人 1: 企业',
  `DIABLED` tinyint(1) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务商证件类型表';

SET FOREIGN_KEY_CHECKS = 1;
