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
DROP TABLE IF EXISTS `t_provider_paper`;
CREATE TABLE `t_provider_paper` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `PAPER_ID` int(11) NOT NULL COMMENT '证书类型ID',
  `PROVIDER_ID` int(11) NOT NULL COMMENT '服务商ID',
  `PIC_PATH` varchar(255) DEFAULT NULL COMMENT '证书图片路径',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务商证件表';

SET FOREIGN_KEY_CHECKS = 1;
