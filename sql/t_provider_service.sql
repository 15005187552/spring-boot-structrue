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
DROP TABLE IF EXISTS `t_provider_service`;
CREATE TABLE `t_provider_service` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `PROVIDER_ID` int(11) NOT NULL COMMENT '服务商ID',
  `SERVICE_ID` int(11) NOT NULL COMMENT '服务分类ID',
  `DISABLED` tinyint(1) DEFAULT '0' COMMENT '是否禁用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务商关联服务分类表';

SET FOREIGN_KEY_CHECKS = 1;
