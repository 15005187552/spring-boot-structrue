/*
Navicat MySQL Data Transfer

Source Server         : janiffy
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : geckdb

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-08-28 16:54:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_add_special
-- ----------------------------
DROP TABLE IF EXISTS `t_add_special`;
CREATE TABLE `t_add_special` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '专项附加扣除分类',
  `NAME` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `SORT` varchar(2) DEFAULT NULL COMMENT '排序',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专项附加扣除分类表';

-- ----------------------------

-- ----------------------------
-- Table structure for t_advertisement
-- ----------------------------
DROP TABLE IF EXISTS `t_advertisement`;
CREATE TABLE `t_advertisement` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '广告ID',
  `PATH` varchar(255) DEFAULT NULL COMMENT '图片路径',
  `URL_PATH` varchar(255) DEFAULT NULL COMMENT '跳转路径',
  `EQUIP_TYPE` int(1) DEFAULT NULL COMMENT '设备类型 对应枚举类型',
  `SORT` int(2) DEFAULT NULL COMMENT '排序',
  `DISABLED` int(1) DEFAULT NULL COMMENT '是否禁用 0-没有禁用 1-禁用',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATER_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATER_ID` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告数据表';

-- ----------------------------
-- Table structure for t_city_item
-- ----------------------------
DROP TABLE IF EXISTS `t_city_item`;
CREATE TABLE `t_city_item` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `ITEM_TYPE` varchar(50) DEFAULT NULL COMMENT '项目类型',
  `REGION_CODE` int(6) DEFAULT NULL COMMENT '地区code',
  `UPPER_LIMIT` varchar(255) DEFAULT NULL COMMENT '上限',
  `LOWER_LIMIT` varchar(255) DEFAULT NULL COMMENT '下限',
  `COMPANY_PER` varchar(255) DEFAULT NULL COMMENT '单位比例',
  `PERSON_PER` varchar(255) DEFAULT NULL COMMENT '个人比例',
  `PER_TYPE` varchar(255) DEFAULT NULL COMMENT '比例类型 0-百分比 1-金额',
  `SORT` varchar(255) DEFAULT NULL COMMENT '排序',
  `CREAT_TIME` datetime DEFAULT NULL,
  `CREATOR` bigint(20) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `UPDATER_ID` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='各城市缴纳项目扣除项表';

-- ----------------------------
=======
>>>>>>> Stashed changes
-- Table structure for t_company
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
-- Table structure for t_company_user
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
-- Table structure for t_guest
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
-- Table structure for t_income_type
-- ----------------------------
DROP TABLE IF EXISTS `t_income_type`;
CREATE TABLE `t_income_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类别ID',
  `NAME` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `SORT` varchar(2) DEFAULT NULL COMMENT '排序',
  `IS_NEED_ENTER` int(1) DEFAULT NULL COMMENT '是否前台输入 0-不需要 1-需要',
  `P_ID` bigint(20) DEFAULT NULL COMMENT '父分类ID',
  `CLASS_DESCRI` varchar(255) DEFAULT NULL COMMENT '分类描述',
  `LEVEL` int(2) DEFAULT NULL COMMENT '级别 0-一级 1-二级',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收入类型表';

-- ----------------------------
-- Table structure for t_location
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
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员自增ID',
  `REG_MOBILE` varchar(255) DEFAULT NULL COMMENT '注册手机号',
  `NICK_NAME` varchar(255) DEFAULT NULL COMMENT '会员昵称',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `DISABLED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';

-- ----------------------------
-- Table structure for t_member_account
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
-- Table structure for t_member_password
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
-- Table structure for t_mobile_code
-- ----------------------------
DROP TABLE IF EXISTS `t_mobile_code`;
CREATE TABLE `t_mobile_code` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `CODE` varchar(6) NOT NULL COMMENT '验证码值',
  `MOBILE` varchar(20) NOT NULL COMMENT '手机号',
  `FROM_IP` varchar(50) DEFAULT NULL COMMENT '来源IP',
  `CREATE_TIME` datetime NOT NULL,
  `DAY_INDEX` int(11) NOT NULL COMMENT '当日重试次数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手机验证码表';

-- ----------------------------
-- Table structure for t_natural_person
-- ----------------------------
DROP TABLE IF EXISTS `t_natural_person`;
CREATE TABLE `t_natural_person` (
  `MEMBER_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `COUNTRY` int(1) DEFAULT NULL COMMENT '国籍 0-中国大陆 1-港澳台 2-外籍',
  `NAME` varchar(255) DEFAULT NULL COMMENT '姓名',
  `CERTIFICATE` int(1) DEFAULT NULL COMMENT '证件类型 0-身份证号 1-港澳台证件号 3-外籍证件号',
  `CERT_NUM` int(50) DEFAULT NULL COMMENT '证件号',
  `CERT_POS_PATH` varchar(255) DEFAULT NULL COMMENT '证件照正面',
  `CERT_OPPO_PATH` varchar(255) DEFAULT NULL COMMENT '证件照反面',
  `DISABLITY_NUM` varchar(50) DEFAULT NULL COMMENT '残疾人证件号',
  `DISABLITY_PATH` varchar(255) DEFAULT NULL COMMENT '残疾证件路径',
  `MATRTYR_NUM` varchar(50) DEFAULT NULL COMMENT '烈属证件号',
  `MATRTYR_PATH` varchar(255) DEFAULT NULL COMMENT '烈属证件路径',
  `OLD_NUM` varchar(50) DEFAULT NULL COMMENT '孤老证件号',
  `OLD_PATH` varchar(255) DEFAULT NULL COMMENT '孤老路径',
  `PROFESSOR_NUM` varchar(50) DEFAULT NULL COMMENT '专家学者证件号',
  `PROFESSOR_PATH` varchar(255) DEFAULT NULL COMMENT '专家学者证件路径',
  `ACADEMIC_NUM` varchar(50) DEFAULT NULL COMMENT '院士证件号',
  `ACADEMIC_PATH` varchar(255) DEFAULT NULL COMMENT '院士证件路径',
  `SOCIAL_SECU` int(1) DEFAULT NULL COMMENT '社保属性 0-公司代缴 1-个人缴纳',
  `CREAT_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`MEMBER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自然人纳税基本信息表';

-- ----------------------------
-- Table structure for t_other_reduce
-- ----------------------------
DROP TABLE IF EXISTS `t_other_reduce`;
CREATE TABLE `t_other_reduce` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '其它扣除减免分类',
  `NAME` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `SORT` varchar(2) DEFAULT NULL COMMENT '排序',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='其它扣除减免分类表';

-- ----------------------------
-- Table structure for t_paper
-- ----------------------------
DROP TABLE IF EXISTS `t_paper`;
CREATE TABLE `t_paper` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL COMMENT '证书名称',
  `TYPE` int(1) DEFAULT NULL COMMENT '适用类型 0: 个人 1: 企业',
  `DIABLED` tinyint(1) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务商证件类型表';

-- ----------------------------
-- Table structure for t_policy_amount
-- ----------------------------
DROP TABLE IF EXISTS `t_policy_amount`;
CREATE TABLE `t_policy_amount` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据ID',
  `USER_ID` int(11) DEFAULT NULL COMMENT '用户ID',
  `PRE_POLICY` varchar(255) DEFAULT NULL COMMENT '政策前税额',
  `POST_POLICY` varchar(255) DEFAULT NULL COMMENT '政策后税额',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='政策前后纳税金额表';

-- ----------------------------
-- Table structure for t_provider
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
-- Table structure for t_provider_paper
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
-- Table structure for t_provider_service
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
-- Table structure for t_provider_user
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
-- Table structure for t_service
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

-- ----------------------------
-- Table structure for t_special_deduction
-- ----------------------------
DROP TABLE IF EXISTS `t_special_deduction`;
CREATE TABLE `t_special_deduction` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '专项扣除分类',
  `NAME` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `SORT` varchar(2) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED COMMENT='专项扣除分类表';

-- ----------------------------
-- Table structure for t_tax
-- ----------------------------
DROP TABLE IF EXISTS `t_tax`;
CREATE TABLE `t_tax` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报税',
  `MEMBER_ID` bigint(20) DEFAULT NULL COMMENT '会员ID',
  `DECLARE_TYPE` varchar(1) DEFAULT NULL COMMENT '申报类型 0-月报 1-年报',
  `DECLARE_TIME` varchar(50) DEFAULT NULL COMMENT '申报时段',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报税数据主表';

-- ----------------------------
-- Table structure for t_tax_income
-- ----------------------------
DROP TABLE IF EXISTS `t_tax_income`;
CREATE TABLE `t_tax_income` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报税数据收入',
  `TAX_ID` bigint(20) DEFAULT NULL COMMENT '报税数据ID',
  `INCOME_TYPE_ID` bigint(20) DEFAULT NULL COMMENT '收入分类ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATER_ID` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  `INCOME` varchar(20) DEFAULT NULL COMMENT '收入金额',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报税数据收入表';

-- ----------------------------
-- Table structure for t_tax_other_reduce
-- ----------------------------
DROP TABLE IF EXISTS `t_tax_other_reduce`;
CREATE TABLE `t_tax_other_reduce` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报税数据其它扣除减免',
  `TAX_ID` bigint(20) DEFAULT NULL COMMENT '报税数据ID',
  `OTHER_REDUCE_ID` bigint(20) DEFAULT NULL COMMENT '其它扣除减免分类ID',
  `TAX_MONEY` varchar(255) DEFAULT NULL COMMENT '个人缴纳金额',
  `TAX_DOC_PATH` varchar(255) DEFAULT NULL COMMENT '缴纳证明路径',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATER_ID` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报税数据其它扣除减免表';

-- ----------------------------
-- Table structure for t_tax_special
-- ----------------------------
DROP TABLE IF EXISTS `t_tax_special`;
CREATE TABLE `t_tax_special` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报税数据专项扣除表',
  `TAX_ID` bigint(20) DEFAULT NULL COMMENT '报税数据ID',
  `SPECIAL_DEDU_ID` bigint(20) DEFAULT NULL COMMENT '专项扣除分类ID',
  `PERSONAL_MONEY` varchar(255) DEFAULT NULL COMMENT '个人缴纳金额',
  `COMPANY_MONEY` varchar(255) DEFAULT NULL COMMENT '单位纳税金额',
  `PERSONAL_PERCENT` varchar(255) DEFAULT NULL COMMENT '个人缴纳比例',
  `COMPANY_PERCENT` varchar(255) DEFAULT NULL COMMENT '单位缴纳比例',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATER_ID` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报税数据专项扣除表';

-- ----------------------------
-- Table structure for t_tax_special_add
-- ----------------------------
DROP TABLE IF EXISTS `t_tax_special_add`;
CREATE TABLE `t_tax_special_add` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报税数据专项附加扣除',
  `TAX_ID` bigint(20) DEFAULT NULL COMMENT '报税数据ID',
  `SPECIAL_ADD_ID` bigint(20) DEFAULT NULL COMMENT '专项附加扣除分类ID',
  `TAX_MONEY` varchar(20) DEFAULT NULL COMMENT '个人缴纳金额',
  `TAX_DOC_PATH` varchar(255) DEFAULT NULL COMMENT '缴纳证明附件路径',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATER_ID` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报税数据专项附加扣除表';
