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

 Date: 08/23/2018 10:47:03 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_company`
-- ----------------------------
DROP TABLE IF EXISTS `t_company`;
CREATE TABLE `t_company` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '入驻企业',
  `NAME` varchar(255) NOT NULL COMMENT '企业名称',
  `TYPE` int(1) DEFAULT NULL COMMENT '企业类型',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `VALIDATE_STATE` int(1) NOT NULL DEFAULT '0' COMMENT '认证状态：0：未认证，1：认证通过，2:认证失败',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `CODE` varchar(255) DEFAULT NULL COMMENT '企业纳税代码',
  `CREATER_ID` bigint(20) NOT NULL COMMENT '创建人ID',
  `PIC_PATH` varchar(255) DEFAULT NULL COMMENT '证照图片路径',
  `VALIDATER_ID` int(11) DEFAULT NULL COMMENT '审核人ID',
  `VALIDATE_TIME` datetime DEFAULT NULL COMMENT '审核时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `VALIDATE_TEXT` varchar(255) DEFAULT NULL COMMENT '审核意见',
  `PROV_CODE` int(6) DEFAULT NULL COMMENT '省级代码',
  `CITY_CODE` int(6) DEFAULT NULL COMMENT '市级代码',
  `AREA_CODE` int(11) DEFAULT NULL COMMENT '区级代码',
  `ADDRESS` varchar(255) DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入驻企业表';

SET FOREIGN_KEY_CHECKS = 1;
