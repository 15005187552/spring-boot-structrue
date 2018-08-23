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

 Date: 08/23/2018 13:41:25 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_mobile_code`
-- ----------------------------
DROP TABLE IF EXISTS `t_mobile_code`;
CREATE TABLE `t_mobile_code` (
  `ID` bigint(20) NOT NULL COMMENT 'ID',
  `CODE` varchar(6) NOT NULL COMMENT '验证码值',
  `MOBILE` varchar(20) NOT NULL COMMENT '手机号',
  `FROM_IP` varchar(50) DEFAULT NULL COMMENT '来源IP',
  `CREATE_TIME` datetime NOT NULL,
  `DAY_INDEX` int(11) NOT NULL COMMENT '当日重试次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手机验证码表';

SET FOREIGN_KEY_CHECKS = 1;
