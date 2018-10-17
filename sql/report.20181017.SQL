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
/*Table structure for table `t_add_special` */

DROP TABLE IF EXISTS `t_add_special`;

CREATE TABLE `t_add_special` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '专项附加扣除分类',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类名称',
  `SORT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '排序',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='专项附加扣除分类表';

/*Data for the table `t_add_special` */

LOCK TABLES `t_add_special` WRITE;

insert  into `t_add_special`(`ID`,`NAME`,`SORT`,`DESCRIPTION`) values (1,'子女教育支出','1',''),(2,'继续教育支出','2',''),(3,'住房贷款利息支出','３',''),(4,'住房租金支出','４',''),(5,'大病医疗支出','５',''),(6,'养老支出','6','');

UNLOCK TABLES;

/*Table structure for table `t_income_type` */

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

/*Data for the table `t_income_type` */

LOCK TABLES `t_income_type` WRITE;

insert  into `t_income_type`(`ID`,`NAME`,`SORT`,`IS_NEED_ENTER`,`P_ID`,`CLASS_DESC`,`LEVEL`) values (1,'综合所得','1',0,NULL,'122',1),(2,'利息股息红利收入','2',1,NULL,'利息股息红利收入',1),(3,'财产转让所得','3',1,NULL,'财产转让所得',1),(4,'财产租赁所得','4',1,NULL,'财产租赁所得',1),(5,'偶然所得','5',1,NULL,'偶然所得',1),(6,'工资薪金收入','1',1,1,'工资薪金收入',1),(7,'基本工资','1',1,6,'基本工资',1),(8,'全年一次性奖金','2',1,6,'全年一次性奖金',1),(9,'解除关系一次性补偿','3',1,6,'解除关系一次性补偿',1),(10,'劳务收入','2',1,1,'劳务收入',1),(11,'稿酬收入','3',1,1,'稿酬收入',1),(12,'特许权使用费收入','4',1,1,'特许权使用费收入',1),(13,'其他所得','６',1,NULL,'其他所得',1);

UNLOCK TABLES;

/*Table structure for table `t_other_reduce` */

DROP TABLE IF EXISTS `t_other_reduce`;

CREATE TABLE `t_other_reduce` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '其它扣除减免分类',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类名称',
  `SORT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '排序',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='其它扣除减免分类表';

/*Data for the table `t_other_reduce` */

LOCK TABLES `t_other_reduce` WRITE;

insert  into `t_other_reduce`(`ID`,`NAME`,`SORT`,`DESCRIPTION`) values (1,'商业健康保险费','１',''),(2,'商业养老保险','２','税延型'),(3,'年金','３','企业年金或职业年金'),(4,'公职人员公务用车／通讯及其他补贴','４',''),(5,'远洋运输船员伙食补贴','５',''),(6,'其他','６','');

UNLOCK TABLES;

/*Table structure for table `t_special_deduction` */

DROP TABLE IF EXISTS `t_special_deduction`;

CREATE TABLE `t_special_deduction` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '专项扣除分类',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类名称',
  `SORT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED COMMENT='专项扣除分类表';

/*Data for the table `t_special_deduction` */

LOCK TABLES `t_special_deduction` WRITE;

insert  into `t_special_deduction`(`ID`,`NAME`,`SORT`) values (1,'养老保险','1'),(2,'医疗保险','3'),(3,'失业保险','3'),(4,'工伤保险','4'),(5,'生育保险','5'),(6,'住房公积金','6');

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
