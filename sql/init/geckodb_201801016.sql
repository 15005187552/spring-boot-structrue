/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.8.167
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : 192.168.8.167
 Source Database       : geckodb

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : utf-8

 Date: 10/16/2018 17:04:20 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `im_customer_message`
-- ----------------------------
DROP TABLE IF EXISTS `im_customer_message`;
CREATE TABLE `im_customer_message` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CUSTOMER_SESSION_ID` bigint(20) DEFAULT NULL COMMENT '属于的会话',
  `TEXT` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '会话内容',
  `RECEIVER_ID` bigint(20) DEFAULT NULL COMMENT '接收者ID，发送者可以为游客，客户，客服',
  `RECEIVER_TYPE` int(11) DEFAULT NULL COMMENT '接收者不同类型',
  `CUSTOMER_STATUS` int(11) DEFAULT NULL COMMENT '客户消息状态（未发，未读，已读）',
  `PROVIDER_STATUS` int(11) DEFAULT NULL COMMENT '服务商（接待处理）状态（未发，未读，已读）',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `PUSH_TIME` datetime DEFAULT NULL COMMENT '推送时间',
  `HAND_TIME` datetime DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客服消息表';

-- ----------------------------
--  Table structure for `im_customer_session`
-- ----------------------------
DROP TABLE IF EXISTS `im_customer_session`;
CREATE TABLE `im_customer_session` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '会话名称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CUSTOMER_MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '客户的会员ID',
  `CUSTOMER_GUEST_ID` bigint(20) DEFAULT NULL COMMENT '客户的游客ID',
  `RECEPTIONIST_MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '当前接待人的会员ID',
  `PROVIDER_ID` bigint(20) DEFAULT NULL COMMENT '当前服务商的ID',
  `STATUS` int(11) DEFAULT NULL COMMENT '会话状态（枚举）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客服会话表';

-- ----------------------------
--  Table structure for `im_push_message`
-- ----------------------------
DROP TABLE IF EXISTS `im_push_message`;
CREATE TABLE `im_push_message` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `MESSAGE` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '消息体',
  `RECEVIER_ID` bigint(20) DEFAULT NULL COMMENT '接受者（ADMIN,CUSTOMER(PC,MP),GUEST,PRIVODER）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='推送消息表';

-- ----------------------------
--  Table structure for `im_push_message_type`
-- ----------------------------
DROP TABLE IF EXISTS `im_push_message_type`;
CREATE TABLE `im_push_message_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PUSH_MESSAGE_ID` bigint(20) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL COMMENT '发送至那个端',
  `PUSH_TIME` datetime DEFAULT NULL COMMENT '推送时间',
  `RAED_TIME` datetime DEFAULT NULL COMMENT '读取时间',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `im_session`
-- ----------------------------
DROP TABLE IF EXISTS `im_session`;
CREATE TABLE `im_session` (
  `ID` bigint(20) NOT NULL COMMENT 'ID',
  `STATUS` varchar(10) DEFAULT NULL COMMENT '状态',
  `TARGET_ID` bigint(20) DEFAULT NULL COMMENT '目标表ID',
  `CONNECT_TIME` datetime DEFAULT NULL COMMENT '连接时间',
  `LAST_CONNECT_TIME` datetime DEFAULT NULL COMMENT '上次连接时间',
  `LAST_IP` varchar(1000) DEFAULT NULL COMMENT '上次连接IP',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='WS_SESSION表';

-- ----------------------------
--  Table structure for `im_session_context`
-- ----------------------------
DROP TABLE IF EXISTS `im_session_context`;
CREATE TABLE `im_session_context` (
  `SESSION_ID` bigint(20) NOT NULL COMMENT 'SESSION_ID 连接记录ID',
  `CONTEXT_ID` varchar(20) DEFAULT NULL COMMENT 'CONTEXT_ID WS 上下文ID',
  `CHANNEL` varchar(64) DEFAULT NULL COMMENT '渠道'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户在线和上下文关联表';

-- ----------------------------
--  Table structure for `im_socket_info`
-- ----------------------------
DROP TABLE IF EXISTS `im_socket_info`;
CREATE TABLE `im_socket_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `TARGET_ID` bigint(20) DEFAULT NULL COMMENT '对应表记录ID',
  `TARGET_TABLE` int(2) DEFAULT NULL COMMENT '对应的记录表Code',
  `STATUS` int(11) DEFAULT NULL COMMENT 'Socket状态 （在线，离线）枚举',
  `CHANNEL` int(11) DEFAULT NULL COMMENT '渠道（CLIENT,MEMBER,ADMIN,PROVIDER）枚举',
  `IP` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '当前IP',
  `CONNECT_TIME` datetime DEFAULT NULL COMMENT '连接时间',
  `CONNECT_CONTENXT_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '连接上下文对应的ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1490 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='套接字连接信息表';

-- ----------------------------
--  Table structure for `t_add_special`
-- ----------------------------
DROP TABLE IF EXISTS `t_add_special`;
CREATE TABLE `t_add_special` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '专项附加扣除分类',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类名称',
  `SORT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '排序',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='专项附加扣除分类表';

-- ----------------------------
--  Table structure for `t_admin`
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `PASSWORD` varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `NICK_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '别称',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `DISABLED` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='后台管理用户表';

-- ----------------------------
--  Table structure for `t_admin_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role`;
CREATE TABLE `t_admin_role` (
  `ROLE_ID` bigint(20) NOT NULL,
  `ADMIN_ID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- ----------------------------
--  Table structure for `t_advertisement`
-- ----------------------------
DROP TABLE IF EXISTS `t_advertisement`;
CREATE TABLE `t_advertisement` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '广告ID',
  `PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图片路径',
  `URL_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '跳转路径',
  `EQUIP_TYPE` int(1) DEFAULT NULL COMMENT '设备类型 对应枚举类型',
  `SORT` int(2) DEFAULT '0' COMMENT '排序',
  `DISABLED` int(1) DEFAULT '0' COMMENT '是否禁用 0-没有禁用 1-禁用',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATER_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATER_ID` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='广告数据表';

-- ----------------------------
--  Table structure for `t_attendance`
-- ----------------------------
DROP TABLE IF EXISTS `t_attendance`;
CREATE TABLE `t_attendance` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `TAX_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '考勤报税数据ID',
  `ATTRIBUTE_ID` bigint(20) DEFAULT NULL COMMENT '属性ID',
  `VALUE` varchar(255) DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_attendance_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `t_attendance_attribute`;
CREATE TABLE `t_attendance_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `IS_NEED_ENTER` tinyint(2) DEFAULT '0' COMMENT '是否需要输出到前台 0-不需要 1-需要',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `DISABLED` tinyint(1) DEFAULT '0' COMMENT '禁用/启用',
  `SORT` tinyint(2) DEFAULT '1' COMMENT '排序',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8 COMMENT='考勤字段表';

-- ----------------------------
--  Table structure for `t_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `t_attribute`;
CREATE TABLE `t_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `TABLE_NAME` int(2) DEFAULT NULL COMMENT '关联哪张表',
  `ITEM_ID` bigint(20) DEFAULT NULL COMMENT '关联表的ID',
  `NAME` varchar(255) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='公司人事模板定制项表';

-- ----------------------------
--  Table structure for `t_cal_tax`
-- ----------------------------
DROP TABLE IF EXISTS `t_cal_tax`;
CREATE TABLE `t_cal_tax` (
  `GUEST_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '游客ID',
  `REGION_CODE` int(6) DEFAULT NULL COMMENT '城市编码',
  `MONEY` decimal(20,4) DEFAULT NULL COMMENT '税前收入',
  `OLD_TAX` decimal(20,4) DEFAULT NULL COMMENT '老个税扣除额',
  `NEW_TAX` decimal(20,4) DEFAULT NULL COMMENT '新个税扣除额',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`GUEST_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='个税计算表';

-- ----------------------------
--  Table structure for `t_city_item`
-- ----------------------------
DROP TABLE IF EXISTS `t_city_item`;
CREATE TABLE `t_city_item` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `ITEM_TYPE` bigint(20) DEFAULT NULL COMMENT '项目类型',
  `REGION_CODE` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地区code',
  `UPPER_LIMIT` decimal(12,2) DEFAULT NULL COMMENT '上限',
  `LOWER_LIMIT` decimal(12,2) DEFAULT NULL COMMENT '下限',
  `COMPANY_PER` decimal(12,4) DEFAULT NULL COMMENT '单位比例',
  `PERSON_PER` decimal(12,4) DEFAULT NULL COMMENT '个人比例',
  `PER_TYPE` int(1) DEFAULT NULL COMMENT '个人类型 0-百分比 1-金额',
  `COM_TYPE` int(1) DEFAULT '0' COMMENT '公司类型 0-百分比 1-金额',
  `SORT` int(10) DEFAULT NULL COMMENT '排序',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATOR` bigint(20) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATER_ID` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  `DISABLED` int(1) DEFAULT NULL COMMENT '是否禁用 0-没有禁用 1-可用',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1917 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='各城市缴纳项目扣除项表';

-- ----------------------------
--  Table structure for `t_company`
-- ----------------------------
DROP TABLE IF EXISTS `t_company`;
CREATE TABLE `t_company` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '入驻企业',
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '企业名称',
  `TYPE` int(1) DEFAULT NULL COMMENT '企业类型',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `VALIDATE_STATE` int(1) NOT NULL DEFAULT '0' COMMENT '认证状态：0：未认证，1：认证通过，2:认证失败',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `PHONE_NUM` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号码',
  `CODE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '企业纳税代码',
  `CREATER_ID` bigint(20) NOT NULL COMMENT '创建人ID',
  `PIC_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证照图片路径',
  `VALIDATOR_ID` bigint(20) DEFAULT NULL COMMENT '审核人ID',
  `VALIDATE_TIME` datetime DEFAULT NULL COMMENT '审核时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `VALIDATE_TEXT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核意见',
  `PROV_CODE` int(6) DEFAULT NULL COMMENT '省级代码',
  `CITY_CODE` int(6) DEFAULT NULL COMMENT '市级代码',
  `AREA_CODE` int(11) DEFAULT NULL COMMENT '区级代码',
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='入驻企业表';

-- ----------------------------
--  Table structure for `t_company_special`
-- ----------------------------
DROP TABLE IF EXISTS `t_company_special`;
CREATE TABLE `t_company_special` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '公司专项扣除表ID',
  `COMPANY_ID` bigint(20) NOT NULL COMMENT '公司ID',
  `SPECIAL_ID` bigint(20) NOT NULL COMMENT '专项扣除ID',
  `COMPANY_PER` decimal(10,4) DEFAULT NULL COMMENT '公司扣除百分比',
  `PERSON_PER` decimal(10,4) DEFAULT NULL COMMENT '个人扣除百分比',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_company_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_company_user`;
CREATE TABLE `t_company_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMPANY_ID` int(20) NOT NULL COMMENT '所属企业ID',
  `MEMBER_ID` bigint(20) NOT NULL COMMENT '会员ID',
  `ROLES_CODE` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '角色代码',
  `CREATE_TIME` datetime NOT NULL COMMENT '加入时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否离职',
  `ACTIVATED` int(1) DEFAULT NULL COMMENT '是否激活',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='企业员工表';

-- ----------------------------
--  Table structure for `t_company_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_company_user_info`;
CREATE TABLE `t_company_user_info` (
  `COMPANY_USER_ID` bigint(20) NOT NULL COMMENT 'ID主键',
  `JOB_NUM` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '工号',
  `EDUCATION` int(2) DEFAULT NULL COMMENT '学历',
  `PERSON_STATE` int(2) DEFAULT NULL COMMENT '人员状态',
  `EMPLOYEE` int(1) DEFAULT NULL COMMENT '是否雇员',
  `HIRE_DATE` date DEFAULT NULL COMMENT '任职日期',
  `EMPLOYEE_TYPE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '员工类别',
  `DEPARTMENT` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门',
  `STATION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '岗位',
  `TERM_DATE` date DEFAULT NULL COMMENT '离职日期',
  `WORK_CITY` varchar(6) DEFAULT NULL COMMENT '城市编码',
  `MARITAL_STATUS` int(1) DEFAULT NULL COMMENT '婚姻状况',
  `FUND_PER` decimal(10,4) DEFAULT NULL COMMENT '公积金比例',
  `FUND_BASE` decimal(10,4) DEFAULT NULL COMMENT '公积金基数',
  `SOCIAL_BASE` decimal(10,4) DEFAULT NULL COMMENT '社保基数',
  `INTRODUCE_TALENTS` int(1) DEFAULT NULL COMMENT '是否引进人才',
  `BANK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '开户银行',
  `BANK_NUM` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '银行账号',
  `SOCIAL_NUM` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '社保账号',
  `FUND_NUM` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '公积金账号',
  `SPECIAL_INDUSTRY` int(1) DEFAULT NULL COMMENT '是否特定行业',
  `IS_INVESTOR` int(1) DEFAULT NULL COMMENT '是否股东投资人',
  `EMAIL` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `REMARK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`COMPANY_USER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='公司员工信息详情表';

-- ----------------------------
--  Table structure for `t_dict`
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `KEY` varchar(64) NOT NULL COMMENT '键',
  `VALUE` longtext NOT NULL COMMENT '值',
  `DESC` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典表';

-- ----------------------------
--  Table structure for `t_form_id`
-- ----------------------------
DROP TABLE IF EXISTS `t_form_id`;
CREATE TABLE `t_form_id` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'FORM_ID ID',
  `FORM_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'FORM_ID',
  `MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '对应用户',
  `STATUS` int(2) DEFAULT NULL COMMENT '状态',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_function`
-- ----------------------------
DROP TABLE IF EXISTS `t_function`;
CREATE TABLE `t_function` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父节点ID',
  `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `TITLE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标头',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `ICON` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标路径',
  `URL` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '链接页面',
  `SORT` int(11) DEFAULT '1' COMMENT '排序',
  `IS_SHOW` tinyint(2) DEFAULT '0' COMMENT '是否展示',
  `DISABLED` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100129 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
--  Table structure for `t_guest`
-- ----------------------------
DROP TABLE IF EXISTS `t_guest`;
CREATE TABLE `t_guest` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '游客自增ID',
  `GUEST_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '游客的UUID, 用于标识游客',
  `SOURCE_TYPE` int(2) NOT NULL COMMENT '游客来源，枚举',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `LAST_ACTIVE_TIME` datetime NOT NULL COMMENT '最后活动时间',
  `EXT_INFO` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '扩展信息',
  `MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '已经转化为会员是的会员ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='游客表';

-- ----------------------------
--  Table structure for `t_income_type`
-- ----------------------------
DROP TABLE IF EXISTS `t_income_type`;
CREATE TABLE `t_income_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类别ID',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类名称',
  `SORT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '排序',
  `IS_NEED_ENTER` int(1) DEFAULT NULL COMMENT '是否前台输入 0-不需要 1-需要',
  `P_ID` bigint(20) DEFAULT NULL COMMENT '父分类ID',
  `CLASS_DESC` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类描述',
  `LEVEL` int(2) DEFAULT NULL COMMENT '级别 0-一级 1-二级  2、三级 ',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='收入类型表';

-- ----------------------------
--  Table structure for `t_location`
-- ----------------------------
DROP TABLE IF EXISTS `t_location`;
CREATE TABLE `t_location` (
  `CODE` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区划代码',
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区划名称',
  `PCODE` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父级区域代码',
  `LEVEL` int(11) DEFAULT NULL COMMENT '区划级别 省 0 市 1 区县 2',
  `DISABLED` tinyint(1) DEFAULT '0' COMMENT '是否禁用',
  PRIMARY KEY (`CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='中国行政区划表';

-- ----------------------------
--  Table structure for `t_member`
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员自增ID',
  `REG_MOBILE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '注册手机号',
  `NICK_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '会员昵称',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `AVATAR_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像路径',
  `MEMBER_IDCARD` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '会员身份证号',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `VALIDATE_STATE` tinyint(2) DEFAULT '0' COMMENT '审核状态 0  初始状态   1 待审核 2 审核通过 3 审核失败',
  `PIC_FRONT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '身份证正面',
  `PIC_BACK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '身份证反面',
  `PIC_PASSPORT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '个人证件照',
  `VERSION` int(5) DEFAULT '1',
  `INFO_VALIDATE_STATE` tinyint(2) DEFAULT '0' COMMENT '会员基本信息认证状态  0  未认证  1 已认证  2  认证失败',
  `VALIDATOR_ID` bigint(20) DEFAULT NULL,
  `VALIDATE_TEXT` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '认证内容',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='会员表';

-- ----------------------------
--  Table structure for `t_member_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_account`;
CREATE TABLE `t_member_account` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账号ID',
  `USERNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号ID',
  `TYPE` int(2) NOT NULL COMMENT '账号类型',
  `MEMBER_ID` bigint(20) NOT NULL COMMENT '所属会员ID',
  `PASSWORD_ID` bigint(20) DEFAULT NULL COMMENT '关联密码ID',
  `EXT_INFO` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '账号扩展信息',
  `ACCOUNT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户账号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='会员账号表';

-- ----------------------------
--  Table structure for `t_member_paper`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_paper`;
CREATE TABLE `t_member_paper` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PAPER_ID` int(11) DEFAULT NULL,
  `MEMBER_ID` bigint(20) DEFAULT NULL,
  `VALIDATE_STATE` tinyint(1) DEFAULT '1' COMMENT '审核状态  1 待审核 2 审核通过 3 审核失败',
  `VALIDATOR_ID` bigint(20) DEFAULT NULL COMMENT '认证人id',
  `VALIDATE_TIME` datetime DEFAULT NULL COMMENT '认证时间',
  `VALIDATE_TEXT` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '认证内容',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `VERSION` int(5) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_member_password`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_password`;
CREATE TABLE `t_member_password` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '密码ID',
  `PASSWORD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码值',
  `SALT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '加密盐值',
  `LAST_MODIFY_TIME` datetime NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='账号密码表';

-- ----------------------------
--  Table structure for `t_mobile_code`
-- ----------------------------
DROP TABLE IF EXISTS `t_mobile_code`;
CREATE TABLE `t_mobile_code` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `CODE` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '验证码值',
  `MOBILE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `FROM_IP` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '来源IP',
  `CREATE_TIME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DAY_INDEX` int(11) NOT NULL COMMENT '当日重试次数',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='手机验证码表';

-- ----------------------------
--  Table structure for `t_natural_person`
-- ----------------------------
DROP TABLE IF EXISTS `t_natural_person`;
CREATE TABLE `t_natural_person` (
  `MEMBER_ID` bigint(20) NOT NULL COMMENT '会员ID',
  `COMPANY_ID` bigint(20) DEFAULT NULL,
  `COUNTRY` int(1) DEFAULT NULL COMMENT '国籍 0-中国大陆 1-港澳台 2-外籍',
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `GENDER` int(1) DEFAULT NULL COMMENT '性别 0-男  1-女',
  `BIRTHDAY` date DEFAULT NULL COMMENT '出生年月',
  `CERTIFICATE` int(1) DEFAULT NULL COMMENT '证件类型 0-身份证号 1-港澳台证件号 3-外籍证件号',
  `PROVINCE` int(6) DEFAULT NULL COMMENT '省',
  `CITY` int(6) DEFAULT NULL COMMENT '城市',
  `AREA` int(6) DEFAULT NULL COMMENT '区',
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '详细地址',
  `CERT_NUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件号',
  `CERT_POS_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件照正面',
  `CERT_OPPO_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件照反面',
  `DISABLITY_NUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '残疾人证件号',
  `DISABLITY_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '残疾证件路径',
  `MATRTYR_NUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '烈属证件号',
  `MATRTYR_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '烈属证件路径',
  `OLD_NUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '孤老证件号',
  `OLD_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '孤老路径',
  `PROFESSOR_NUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '专家学者证件号',
  `PROFESSOR_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '专家学者证件路径',
  `ACADEMIC_NUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '院士证件号',
  `ACADEMIC_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '院士证件路径',
  `SOCIAL_SECU` int(1) DEFAULT NULL COMMENT '社保属性 0-公司代缴 1-个人缴纳',
  `CREAT_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`MEMBER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='自然人纳税基本信息表';

-- ----------------------------
--  Table structure for `t_natural_person_backup`
-- ----------------------------
DROP TABLE IF EXISTS `t_natural_person_backup`;
CREATE TABLE `t_natural_person_backup` (
  `ID` bigint(20) DEFAULT NULL,
  `MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '会员ID',
  `COMPANY_ID` bigint(20) DEFAULT NULL,
  `TAX_ID` bigint(20) DEFAULT NULL,
  `COUNTRY` int(1) DEFAULT NULL COMMENT '国籍 0-中国大陆 1-港澳台 2-外籍',
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `GENDER` int(1) DEFAULT NULL COMMENT '性别 0-男  1-女',
  `BIRTHDAY` datetime DEFAULT NULL COMMENT '出生年月',
  `CERTIFICATE` int(1) DEFAULT NULL COMMENT '证件类型 0-身份证号 1-港澳台证件号 3-外籍证件号',
  `PROVINCE` int(6) DEFAULT NULL COMMENT '省',
  `CITY` int(6) DEFAULT NULL COMMENT '城市',
  `AREA` int(6) DEFAULT NULL COMMENT '区',
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '详细地址',
  `CERT_NUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件号',
  `CERT_POS_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件照正面',
  `CERT_OPPO_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件照反面',
  `DISABLITY_NUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '残疾人证件号',
  `DISABLITY_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '残疾证件路径',
  `MATRTYR_NUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '烈属证件号',
  `MATRTYR_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '烈属证件路径',
  `OLD_NUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '孤老证件号',
  `OLD_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '孤老路径',
  `PROFESSOR_NUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '专家学者证件号',
  `PROFESSOR_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '专家学者证件路径',
  `ACADEMIC_NUM` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '院士证件号',
  `ACADEMIC_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '院士证件路径',
  `SOCIAL_SECU` int(1) DEFAULT NULL COMMENT '社保属性 0-公司代缴 1-个人缴纳',
  `CREAT_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='自然人纳税基本信息表';

-- ----------------------------
--  Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(255) DEFAULT NULL COMMENT '公告标题',
  `CONTENT` text COMMENT '公告内容',
  `TAG_ID` int(11) DEFAULT NULL COMMENT '标签id',
  `SORT` int(11) DEFAULT NULL COMMENT '排序',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DISABLED` tinyint(1) DEFAULT '0' COMMENT '启用禁用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='公告表';

-- ----------------------------
--  Table structure for `t_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `ORDER_NO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '订单号',
  `MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '用户id',
  `SHIPPING_ID` bigint(20) DEFAULT NULL,
  `PAYMENT` decimal(20,2) DEFAULT NULL COMMENT '实际付款金额,单位是元,保留两位小数',
  `PAYMENT_TYPE` int(4) DEFAULT NULL COMMENT '支付类型,1-在线支付',
  `STATUS` int(5) DEFAULT NULL COMMENT '订单状态:0-已取消,5-待服务定价,10-未付款,20-已付款,50-交易成功,60-交易关闭',
  `PAYMENT_TIME` datetime DEFAULT NULL COMMENT '支付时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '交易完成时间',
  `CLOSE_TIME` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DOWN_PAYMENT_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '首付金额',
  `REMIAN_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '剩余金额',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `order_no_index` (`ORDER_NO`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_order_comments`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_comments`;
CREATE TABLE `t_order_comments` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品评价ID',
  `ORDER_ID` bigint(20) DEFAULT NULL COMMENT '关联订单ID',
  `ORDER_ITEM_ID` bigint(20) DEFAULT NULL COMMENT '关联的订单明细ID',
  `MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '评价的用户',
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名/昵称',
  `STAR` int(1) DEFAULT '5' COMMENT '评价打分',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '评价创建时间',
  `PROVIDER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单评价表';

-- ----------------------------
--  Table structure for `t_order_comments_path`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_comments_path`;
CREATE TABLE `t_order_comments_path` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评价图片ID',
  `COMMENT_ID` bigint(20) DEFAULT NULL COMMENT '关联的评价ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '评价创建时间',
  `PIC_PATH` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单评价图片表';

-- ----------------------------
--  Table structure for `t_order_item`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单子表id',
  `ORDER_NO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `SERVICE_ID` int(11) DEFAULT NULL COMMENT '服务id',
  `GOOD_ID` bigint(20) DEFAULT NULL COMMENT '商品id',
  `CURRENT_UNIT_PRICE` decimal(20,2) DEFAULT NULL COMMENT '生成订单时的商品单价，单位是元,保留两位小数',
  `QUANTITY` int(10) DEFAULT '1' COMMENT '商品数量',
  `TOTAL_PRICE` decimal(20,2) DEFAULT NULL COMMENT '商品总价,单位是元,保留两位小数',
  `SERVICE_CONTENT` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '服务订单备注',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `ORDER_ITEM_NO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '子订单号',
  `ORDER_ITEM_STATUS` int(11) DEFAULT NULL COMMENT '订单状态:0-已取消,5-待服务定价,10-未付款,20-已付款,50-交易成功,60-交易关闭',
  `PROVIDER_ID` bigint(20) DEFAULT NULL COMMENT '服务商id',
  `DOWN_PAYMENT_RATE` decimal(3,2) DEFAULT NULL COMMENT '首付比率',
  `DOWN_PAYMENT_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '首付金额',
  `REMAIN_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '剩余金额',
  `MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '会员id',
  `SPEC_SERVICE_ID` bigint(20) DEFAULT NULL COMMENT '商品规格id',
  `SERVICE_NAME` varchar(255) DEFAULT NULL COMMENT '服务名称',
  `MIN_MONEY` decimal(10,2) DEFAULT NULL,
  `PROVIDER_CONTENT` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `order_no_index` (`ORDER_NO`) USING BTREE,
  KEY `order_no_user_id_index` (`ORDER_NO`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_order_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_log`;
CREATE TABLE `t_order_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单日志ID',
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日志内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `operator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作人',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作人对应用户，不一定有',
  `to_status` int(11) DEFAULT NULL COMMENT '操作后状态',
  `order_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '对应订单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单日志表';

-- ----------------------------
--  Table structure for `t_order_pay_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_pay_info`;
CREATE TABLE `t_order_pay_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '会员id',
  `ORDER_NO` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `WX_ORDER_NUM` varchar(50) DEFAULT NULL COMMENT '微信订单号',
  `STATUS` int(11) DEFAULT '0' COMMENT '支付状态  0  未支付  1  已支付',
  `TYPE` tinyint(2) DEFAULT NULL COMMENT '类型  0  首付款   1 尾款',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `PAY_AMOUNT` decimal(20,2) DEFAULT NULL COMMENT '支付金额',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_other_reduce`
-- ----------------------------
DROP TABLE IF EXISTS `t_other_reduce`;
CREATE TABLE `t_other_reduce` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '其它扣除减免分类',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类名称',
  `SORT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '排序',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='其它扣除减免分类表';

-- ----------------------------
--  Table structure for `t_paper`
-- ----------------------------
DROP TABLE IF EXISTS `t_paper`;
CREATE TABLE `t_paper` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证书名称',
  `TYPE` int(1) DEFAULT NULL COMMENT '适用类型 0: 个人 1: 企业',
  `DIABLED` tinyint(1) DEFAULT '0',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='服务商证件类型表';

-- ----------------------------
--  Table structure for `t_paper_path`
-- ----------------------------
DROP TABLE IF EXISTS `t_paper_path`;
CREATE TABLE `t_paper_path` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MEMBER_PAPER_ID` bigint(20) NOT NULL COMMENT '证件类型ID',
  `PIC_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件文件路径',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='服务商证书表';

-- ----------------------------
--  Table structure for `t_provider`
-- ----------------------------
DROP TABLE IF EXISTS `t_provider`;
CREATE TABLE `t_provider` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '入驻服务商',
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务商名称',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `VALIDATE_STATE` int(1) NOT NULL DEFAULT '1' COMMENT '审核状态 1待审核 2 审核通过 3 审核失败',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `TYPE` int(1) DEFAULT NULL COMMENT '服务商类型(个人：0，机构：1)',
  `CREATOR_ID` bigint(20) NOT NULL COMMENT '创建人ID',
  `VALIDATOR_ID` bigint(20) DEFAULT NULL COMMENT '验证人id',
  `VALIDATE_TIME` datetime DEFAULT NULL COMMENT '验证时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `VALIDATE_TEXT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '验证内容',
  `PROV_CODE` int(6) DEFAULT NULL COMMENT '省code',
  `CITY_CODE` int(6) DEFAULT NULL COMMENT '市code',
  `AREA_CODE` int(6) DEFAULT NULL COMMENT '区code',
  `ADDRESS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '详细地址',
  `PIC_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '营业执照路径',
  `VERSION` int(5) DEFAULT '1',
  `LOGO` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `INSTRO` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '服务商简介',
  `INFO_VALIDATE_STATE` tinyint(2) DEFAULT '0',
  `CASH_DEPOSIT` decimal(10,2) DEFAULT '0.00' COMMENT '保证金',
  `DETAIL` text,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='服务商表';

-- ----------------------------
--  Table structure for `t_provider_goods`
-- ----------------------------
DROP TABLE IF EXISTS `t_provider_goods`;
CREATE TABLE `t_provider_goods` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '服务明细id',
  `SERVICE_ID` int(11) NOT NULL COMMENT '服务id,对应t_service表的主键',
  `PROVIDER_ID` bigint(20) NOT NULL COMMENT '服务商id',
  `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `SUBTITLE` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品副标题',
  `MAIN_IMAGE` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '产品主图,url相对地址',
  `DETAIL` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '商品详情',
  `PRICE` decimal(20,2) NOT NULL COMMENT '价格,单位-元保留两位小数',
  `STATUS` int(6) DEFAULT '1' COMMENT '商品状态.1-在售 2-下架 3-删除',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `DOWM_PAYMENT_RATE` decimal(3,2) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_provider_goods_path`
-- ----------------------------
DROP TABLE IF EXISTS `t_provider_goods_path`;
CREATE TABLE `t_provider_goods_path` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `GOOD_ID` bigint(20) NOT NULL COMMENT '证件类型ID',
  `PIC_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件文件路径',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='服务商证书表';

-- ----------------------------
--  Table structure for `t_provider_paper`
-- ----------------------------
DROP TABLE IF EXISTS `t_provider_paper`;
CREATE TABLE `t_provider_paper` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PAPER_ID` int(11) NOT NULL COMMENT '证件类型ID',
  `PROVIDER_ID` int(11) DEFAULT NULL COMMENT '所属服务商',
  `PIC_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件文件路径',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='服务商证书表';

-- ----------------------------
--  Table structure for `t_provider_services`
-- ----------------------------
DROP TABLE IF EXISTS `t_provider_services`;
CREATE TABLE `t_provider_services` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `SERVICE_ID` int(11) NOT NULL COMMENT '服务分类ID',
  `PROVIDER_ID` bigint(11) NOT NULL COMMENT '服务商ID',
  `VALIDATE_STATE` tinyint(1) DEFAULT '1' COMMENT '审核状态  1 待审核 2 审核通过 3 审核失败',
  `VALIDATOR_ID` bigint(20) DEFAULT NULL COMMENT '认证人id',
  `VALIDATE_TIME` datetime DEFAULT NULL COMMENT '认证时间',
  `VALIDATE_TEXT` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '认证内容',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `VERSION` int(5) DEFAULT NULL,
  `DISABLED` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='服务商关联服务分类表';

-- ----------------------------
--  Table structure for `t_provider_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_provider_user`;
CREATE TABLE `t_provider_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROVIDER_ID` bigint(11) NOT NULL COMMENT '所属服务商ID',
  `MEMBER_ID` bigint(20) NOT NULL COMMENT '服务商关联会员ID',
  `ROLES_CODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '角色代码',
  `CREATE_TIME` datetime NOT NULL COMMENT '加入时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='服务商员工表';

-- ----------------------------
--  Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `ROLE_CODE` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色代码',
  `ROLE_DESC` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色描述',
  `DISABLED` tinyint(2) DEFAULT '0' COMMENT '是否能够删除',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20001 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
--  Table structure for `t_role_function`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_function`;
CREATE TABLE `t_role_function` (
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  `FUNCTION_ID` bigint(20) NOT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色权限关联表';

-- ----------------------------
--  Table structure for `t_service_type`
-- ----------------------------
DROP TABLE IF EXISTS `t_service_type`;
CREATE TABLE `t_service_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务分类名称',
  `MIN_MONEY` decimal(10,0) DEFAULT NULL COMMENT '最低额度',
  `AVATAR_PATH` varchar(255) DEFAULT NULL COMMENT '头像路径',
  `PID` int(11) DEFAULT NULL COMMENT '父级服务分类ID',
  `LEVEL` int(11) DEFAULT NULL COMMENT '分类级别 一级 0 二级 1 ',
  `DISABLED` tinyint(1) DEFAULT '0' COMMENT '是否禁用',
  `IS_TOP` tinyint(2) DEFAULT '0' COMMENT '0  不置顶   1  置顶',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='服务分类表';

-- ----------------------------
--  Table structure for `t_spec`
-- ----------------------------
DROP TABLE IF EXISTS `t_spec`;
CREATE TABLE `t_spec` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SERVICE_ID` int(11) DEFAULT NULL COMMENT '服务类型',
  `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格名称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `SORT` int(11) DEFAULT NULL COMMENT '排序',
  `PID` int(11) DEFAULT NULL COMMENT '父节点id',
  `LEVEL` varchar(255) DEFAULT NULL COMMENT '级别',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_spec_item`
-- ----------------------------
DROP TABLE IF EXISTS `t_spec_item`;
CREATE TABLE `t_spec_item` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SPEC_ID` int(11) DEFAULT NULL COMMENT '规格类型id',
  `ITEM` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格项',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `SORT` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_spec_services_price`
-- ----------------------------
DROP TABLE IF EXISTS `t_spec_services_price`;
CREATE TABLE `t_spec_services_price` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `KEY` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格键名',
  `KEY_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格键名中文',
  `PRICE` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `DOWN_PAYMENT_RATE` decimal(10,2) DEFAULT NULL COMMENT '首付比率',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `PROVIDER_ID` bigint(20) DEFAULT NULL COMMENT '服务商id',
  `DISABLED` tinyint(2) DEFAULT '1' COMMENT '是否启用   0  启用  1 不启用',
  `GOOD_ID` bigint(20) DEFAULT NULL COMMENT '商品明细id',
  `SERVICE_ID` int(11) DEFAULT NULL COMMENT '服务类型id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_special_deduction`
-- ----------------------------
DROP TABLE IF EXISTS `t_special_deduction`;
CREATE TABLE `t_special_deduction` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '专项扣除分类',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类名称',
  `SORT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED COMMENT='专项扣除分类表';

-- ----------------------------
--  Table structure for `t_tax`
-- ----------------------------
DROP TABLE IF EXISTS `t_tax`;
CREATE TABLE `t_tax` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报税',
  `MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '会员ID',
  `DECLARE_TYPE` int(1) DEFAULT NULL COMMENT '申报类型 0-月报 1-年报',
  `DECLARE_TIME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '申报时段',
  `BEFORE_TAX` decimal(10,4) DEFAULT NULL COMMENT '税前金额',
  `AFTER_TAX` decimal(10,4) DEFAULT NULL COMMENT '税后金额',
  `STATUS` int(2) DEFAULT NULL COMMENT '数据状态',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='考勤报税数据主表';

-- ----------------------------
--  Table structure for `t_tax_income`
-- ----------------------------
DROP TABLE IF EXISTS `t_tax_income`;
CREATE TABLE `t_tax_income` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报税数据收入',
  `TAX_ID` bigint(20) DEFAULT NULL COMMENT '报税数据ID',
  `INCOME_TYPE_ID` bigint(20) DEFAULT NULL COMMENT '收入分类ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATER` bigint(20) DEFAULT NULL COMMENT '更新人',
  `INCOME` decimal(20,4) DEFAULT NULL COMMENT '收入金额',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `t_tax_income_taxid_incomeTypeId` (`TAX_ID`,`INCOME_TYPE_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='报税数据收入表';

-- ----------------------------
--  Table structure for `t_tax_other_reduce`
-- ----------------------------
DROP TABLE IF EXISTS `t_tax_other_reduce`;
CREATE TABLE `t_tax_other_reduce` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报税数据其它扣除减免',
  `TAX_ID` bigint(20) DEFAULT NULL COMMENT '报税数据ID',
  `OTHER_REDUCE_ID` bigint(20) DEFAULT NULL COMMENT '其它扣除减免分类ID',
  `TAX_MONEY` decimal(10,4) DEFAULT NULL COMMENT '个人缴纳金额',
  `TAX_DOC_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '缴纳证明路径',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATER` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='报税数据其它扣除减免表';

-- ----------------------------
--  Table structure for `t_tax_special`
-- ----------------------------
DROP TABLE IF EXISTS `t_tax_special`;
CREATE TABLE `t_tax_special` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报税数据专项扣除表',
  `TAX_ID` bigint(20) DEFAULT NULL COMMENT '报税数据ID',
  `SPECIAL_DEDU_ID` bigint(20) DEFAULT NULL COMMENT '专项扣除分类ID',
  `PERSONAL_MONEY` decimal(10,4) DEFAULT NULL COMMENT '个人缴纳金额',
  `COMPANY_MONEY` decimal(10,4) DEFAULT NULL COMMENT '单位纳税金额',
  `PERSONAL_PERCENT` decimal(10,4) DEFAULT NULL COMMENT '个人缴纳比例',
  `COMPANY_PERCENT` decimal(10,4) DEFAULT NULL COMMENT '单位缴纳比例',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATER` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='报税数据专项扣除表';

-- ----------------------------
--  Table structure for `t_tax_special_add`
-- ----------------------------
DROP TABLE IF EXISTS `t_tax_special_add`;
CREATE TABLE `t_tax_special_add` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报税数据专项附加扣除',
  `TAX_ID` bigint(20) DEFAULT NULL COMMENT '报税数据ID',
  `SPECIAL_ADD_ID` bigint(20) DEFAULT NULL COMMENT '专项附加扣除分类ID',
  `TAX_MONEY` decimal(10,4) DEFAULT NULL COMMENT '个人缴纳金额',
  `TAX_DOC_PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '缴纳证明附件路径',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATER` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='报税数据专项附加扣除表';

-- ----------------------------
--  Table structure for `t_template`
-- ----------------------------
DROP TABLE IF EXISTS `t_template`;
CREATE TABLE `t_template` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `COMPANY_ID` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `ATTRIBUTE_ID` bigint(20) DEFAULT NULL COMMENT '模板表头ID',
  `SORT` int(11) DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='模板表';

SET FOREIGN_KEY_CHECKS = 1;
