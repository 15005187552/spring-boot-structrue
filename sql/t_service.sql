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

 Date: 08/23/2018 10:58:51 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_provider_service`
-- ----------------------------
DROP TABLE IF EXISTS `t_service`;
CREATE TABLE `t_service` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `NAME` varchar(255) NOT NULL COMMENT '服务分类名称',
  `PID` int(11) DEFAULT NULL COMMENT '父级服务分类ID',
  `LEVEL` int(11) DEFAULT NULL COMMENT '分类级别 一级 0 二级 1 ',
  `DISABLED` tinyint(1) DEFAULT '0' COMMENT '是否禁用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务分类表';

SET FOREIGN_KEY_CHECKS = 1;
