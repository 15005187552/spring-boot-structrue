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

 Date: 08/23/2018 10:46:08 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_provider`
-- ----------------------------
DROP TABLE IF EXISTS `t_provider`;
CREATE TABLE `t_provider` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '入驻服务商',
  `NAME` varchar(255) NOT NULL COMMENT '服务商名称',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `VALIDATE_STATE` int(1) NOT NULL DEFAULT '0' COMMENT '审核状态 0 待审核 1 审核通过 2 审核失败',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `TYPE` int(1) DEFAULT NULL COMMENT '服务商类型(个人：0，机构：1)',
  `CREATER_ID` bigint(20) NOT NULL COMMENT '创建人ID',
  `VALIDATER_ID` int(11) DEFAULT NULL COMMENT '审核人ID',
  `VALIDATE_TIME` datetime DEFAULT NULL COMMENT '审核时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `VALIDATE_TEXT` varchar(255) DEFAULT NULL COMMENT '审核结果',
  `PROV_CODE` int(6) DEFAULT NULL COMMENT '省级代码',
  `CITY_CODE` int(6) DEFAULT NULL COMMENT '市级代码',
  `AREA_CODE` int(11) DEFAULT NULL COMMENT '区级代码',
  `ADDRESS` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `LOGO_PATH` varchar(255) DEFAULT NULL COMMENT 'LOGO地址',
  `SERVICE_PHONE` varchar(255) DEFAULT NULL COMMENT '服务电话',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务商表';

SET FOREIGN_KEY_CHECKS = 1;
