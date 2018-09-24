/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : 127.0.0.1:3306
 Source Schema         : geckodb

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 24/09/2018 20:37:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for im_customer_message
-- ----------------------------
DROP TABLE IF EXISTS `im_customer_message`;
CREATE TABLE `im_customer_message` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CUSTOMER_SESSION_ID` bigint(20) DEFAULT NULL COMMENT '属于的会话',
  `SENDER_ID` bigint(20) DEFAULT NULL COMMENT '发送者ID，发送者可以为游客，客户，客服',
  `SENDER_TYPE` int(11) DEFAULT NULL COMMENT '发送者不同类型',
  `CUSTOMER_STATUS` int(11) DEFAULT NULL COMMENT '客户消息状态（未发，未读，已读）',
  `PROVIDER_STATUS` int(11) DEFAULT NULL COMMENT '服务商（接待处理）状态（未发，未读，已读）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `PUSH_TIME` datetime DEFAULT NULL COMMENT '推送时间',
  `HAND_TIME` datetime DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='客服消息表';

-- ----------------------------
-- Table structure for im_customer_session
-- ----------------------------
DROP TABLE IF EXISTS `im_customer_session`;
CREATE TABLE `im_customer_session` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(64) DEFAULT NULL COMMENT '会话名称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CUSTOMER_MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '客户的会员ID',
  `CUSTOMER_GUEST_ID` bigint(20) DEFAULT NULL COMMENT '客户的游客ID',
  `RECEPTIONIST_MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '当前接待人的会员ID',
  `PROVIDER_ID` bigint(20) DEFAULT NULL COMMENT '当前服务商的ID',
  `STATUS` int(11) DEFAULT NULL COMMENT '会话状态（枚举）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='客服会话表';

-- ----------------------------
-- Table structure for im_push_message
-- ----------------------------
DROP TABLE IF EXISTS `im_push_message`;
CREATE TABLE `im_push_message` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `MESSAGE` text COMMENT '消息体',
  `RECEVIER_ID` bigint(20) DEFAULT NULL COMMENT '接受者（ADMIN,CUSTOMER(PC,MP),GUEST,PRIVODER）',
  `TYPE` int(11) DEFAULT NULL COMMENT '消息类型（不同类型映射接受者查询不同的表）',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态（推送状态枚举）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `PUSH_TIME` datetime DEFAULT NULL COMMENT '推送时间',
  `READ_TIME` datetime DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='推送消息表';

-- ----------------------------
-- Table structure for im_socket_info
-- ----------------------------
DROP TABLE IF EXISTS `im_socket_info`;
CREATE TABLE `im_socket_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `TARGET_ID` bigint(20) DEFAULT NULL COMMENT '对应表记录ID',
  `TARGET_TABLE` bigint(64) DEFAULT NULL COMMENT '对应的记录表',
  `STATUS` int(11) DEFAULT NULL COMMENT 'Socket状态 （在线，离线）枚举',
  `CHANNEL` int(11) DEFAULT NULL COMMENT '渠道（PC,MEMBER,ADMIN,PRIVADER）枚举',
  `IP` varchar(32) DEFAULT NULL COMMENT '当前IP',
  `LAST_IP` varchar(32) DEFAULT NULL COMMENT '上次IP',
  `CONNECT_TIME` datetime DEFAULT NULL COMMENT '连接时间',
  `CONNECT_CONTENXT_ID` varchar(64) DEFAULT NULL COMMENT '连接上下文对应的ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='套接字连接信息表';

SET FOREIGN_KEY_CHECKS = 1;
