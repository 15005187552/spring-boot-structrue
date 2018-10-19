/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 8.0.12 : Database - geckodb
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Data for the table `t_protocol` */

LOCK TABLES `t_protocol` WRITE;

CREATE TABLE `t_protocol` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(60) DEFAULT NULL COMMENT '协议代码',
  `NAME` varchar(55) DEFAULT NULL COMMENT '协议名称',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '协议一句话描述',
  `CONTENT` text COMMENT '协议主题内容',
  `PIC_PATH` varchar(255) DEFAULT NULL COMMENT '协议图片',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统自定义协议表';

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
