/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 5.7.20-log : Database - geckodb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `ID` varchar(50) NOT NULL,
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '用户名',
  `PASSWORD` varchar(65) DEFAULT NULL COMMENT '密码',
  `NICK_NAME` varchar(50) DEFAULT NULL COMMENT '别称',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `DISABLED` tinyint(2) DEFAULT '0' COMMENT '启用禁用标示',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理用户表';

/*Table structure for table `t_admin_role` */

DROP TABLE IF EXISTS `t_admin_role`;

CREATE TABLE `t_admin_role` (
  `ROLE_ID` varchar(50) NOT NULL,
  `ADMIN_ID` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

/*Table structure for table `t_function` */

DROP TABLE IF EXISTS `t_function`;

CREATE TABLE `t_function` (
  `ID` varchar(50) NOT NULL COMMENT '菜单ID',
  `PARENT_ID` int(1) DEFAULT NULL COMMENT '父节点ID',
  `NAME` varchar(100) NOT NULL COMMENT '名称',
  `TITLE` varchar(100) DEFAULT NULL COMMENT '标头',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `ICON` varchar(100) DEFAULT NULL COMMENT '图标路径',
  `URL` varchar(100) DEFAULT NULL COMMENT '链接页面',
  `SORT` int(11) DEFAULT '1' COMMENT '排序',
  `DISABLED` tinyint(1) DEFAULT '0' COMMENT '启用禁用标识 0启用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `ID` varchar(50) NOT NULL,
  `ROLE_NAME` varchar(55) DEFAULT NULL,
  `DISABLED` tinyint(1) DEFAULT '0' COMMENT '启用禁用表示 0启用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Table structure for table `t_role_function` */

DROP TABLE IF EXISTS `t_role_function`;

CREATE TABLE `t_role_function` (
  `ROLE_ID` varchar(50) NOT NULL COMMENT '角色ID',
  `FUNCTION_ID` varchar(50) NOT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关联表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
