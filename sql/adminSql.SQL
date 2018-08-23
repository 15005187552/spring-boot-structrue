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
CREATE DATABASE /*!32312 IF NOT EXISTS*/`geckodb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `geckodb`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '用户名',
  `PASSWORD` varchar(65) DEFAULT NULL COMMENT '密码',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `DISABLED` tinyint(2) DEFAULT NULL COMMENT '启用禁用标示',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理用户表';

/*Data for the table `t_admin` */

LOCK TABLES `t_admin` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_admin_role` */

DROP TABLE IF EXISTS `t_admin_role`;

CREATE TABLE `t_admin_role` (
  `ROLE_ID` int(11) DEFAULT NULL,
  `ADMIN_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_admin_role` */

LOCK TABLES `t_admin_role` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_funtion` */

DROP TABLE IF EXISTS `t_funtion`;

CREATE TABLE `t_funtion` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `PARENT_ID` int(1) DEFAULT NULL COMMENT '父节点ID',
  `NAME` varchar(100) DEFAULT NULL COMMENT '名称',
  `ICON` varchar(100) DEFAULT NULL COMMENT '图标路径',
  `url` varchar(100) DEFAULT NULL COMMENT '链接页面',
  `SM1` varchar(200) DEFAULT NULL COMMENT '菜单说明1',
  `SM2` varchar(200) DEFAULT NULL COMMENT '菜单说明2',
  `SM3` varchar(200) DEFAULT NULL COMMENT '菜单说明3',
  `SORT` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_funtion` */

LOCK TABLES `t_funtion` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(55) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

LOCK TABLES `t_role` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_role_function` */

DROP TABLE IF EXISTS `t_role_function`;

CREATE TABLE `t_role_function` (
  `ROLE_ID` int(11) DEFAULT NULL COMMENT '角色ID',
  `FUNCTION_ID` int(11) DEFAULT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role_function` */

LOCK TABLES `t_role_function` WRITE;

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
