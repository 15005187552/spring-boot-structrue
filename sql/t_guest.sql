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

 Date: 08/23/2018 13:41:00 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_guest`
-- ----------------------------
DROP TABLE IF EXISTS `t_guest`;
CREATE TABLE `t_guest` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '游客自增ID',
  `GUEST_ID` varchar(100) NOT NULL COMMENT '游客的UUID, 用于标识游客',
  `SOURCE_TYPE` int(2) NOT NULL COMMENT '游客来源，枚举',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `LAST_ACTIVE_TIME` datetime NOT NULL COMMENT '最后活动时间',
  `EXT_INFO` longtext COMMENT '扩展信息',
  `MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '已经转化为会员是的会员ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='游客表';

SET FOREIGN_KEY_CHECKS = 1;
