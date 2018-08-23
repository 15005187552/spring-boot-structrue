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
--  Table structure for `t_location`
-- ----------------------------
DROP TABLE IF EXISTS `t_location`;
CREATE TABLE `t_location` (
  `CODE` int(6) NOT NULL COMMENT '区划代码',
  `NAME` varchar(255) NOT NULL COMMENT '区划名称',
  `PCODE` int(11) DEFAULT NULL COMMENT '父级区域代码',
  `LEVEL` int(11) DEFAULT NULL COMMENT '区划级别 省 0 市 1 区县 2',
  `DISABLED` tinyint(1) DEFAULT '0' COMMENT '是否禁用',
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='中国行政区划表';

SET FOREIGN_KEY_CHECKS = 1;
