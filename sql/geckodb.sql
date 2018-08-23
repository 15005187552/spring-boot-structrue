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

 Date: 08/23/2018 17:06:52 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_admin`
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理用户表';

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

-- ----------------------------
--  Table structure for `t_company_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_company_user`;
CREATE TABLE `t_company_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMPANY_ID` int(11) NOT NULL COMMENT '所属企业ID',
  `MEMBER_ID` bigint(20) NOT NULL COMMENT '会员ID',
  `ROLES_CODE` smallint(6) NOT NULL DEFAULT '0' COMMENT '角色代码',
  `CREATE_TIME` datetime NOT NULL COMMENT '加入时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否离职',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业员工表';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游客表';

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

-- ----------------------------
--  Table structure for `t_member`
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `ID` bigint(20) NOT NULL COMMENT '会员自增ID',
  `REG_MOBILE` varchar(255) DEFAULT NULL COMMENT '注册手机号',
  `NICK_NAME` varchar(255) DEFAULT NULL COMMENT '会员昵称',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';

-- ----------------------------
--  Table structure for `t_member_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_account`;
CREATE TABLE `t_member_account` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账号ID',
  `USERNAME` varchar(255) NOT NULL COMMENT '账号ID',
  `TYPE` int(2) NOT NULL COMMENT '账号类型',
  `MEMBER_ID` bigint(20) NOT NULL COMMENT '所属会员ID',
  `PASSWORD_ID` bigint(20) NOT NULL COMMENT '关联密码ID',
  `EXT_INFO` longtext COMMENT '账号扩展信息',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员账号表';

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
  `VALIDATER_ID` int(11) DEFAULT NULL,
  `VALIDATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `VALIDATE_TEXT` varchar(255) DEFAULT NULL,
  `PROV_CODE` int(6) DEFAULT NULL,
  `CITY_CODE` int(6) DEFAULT NULL,
  `AREA_CODE` int(6) DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务商表';

-- ----------------------------
--  Table structure for `t_provider_paper`
-- ----------------------------
DROP TABLE IF EXISTS `t_provider_paper`;
CREATE TABLE `t_provider_paper` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PAPER_ID` int(11) NOT NULL COMMENT '证件类型ID',
  `PROVIDER_ID` int(11) DEFAULT NULL COMMENT '所属服务商',
  `PIC_PATH` varchar(255) DEFAULT NULL COMMENT '证件文件路径',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务商证书表';

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

-- ----------------------------
--  Table structure for `t_provider_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_provider_user`;
CREATE TABLE `t_provider_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROVIDER_ID` int(11) NOT NULL COMMENT '所属服务商ID',
  `MEMBER_ID` bigint(20) NOT NULL COMMENT '服务商关联会员ID',
  `ROLES_CODE` smallint(6) NOT NULL DEFAULT '0' COMMENT '角色代码',
  `CREATE_TIME` datetime NOT NULL COMMENT '加入时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务商员工表';

-- ----------------------------
--  Table structure for `t_service`
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
