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

/*Data for the table `t_attendance_attribute` */

LOCK TABLES `t_attendance_attribute` WRITE;

insert  into `t_attendance_attribute`(`ID`,`NAME`,`DESCRIPTION`,`IS_NEED_ENTER`,`CREATE_TIME`,`UPDATE_TIME`,`DISABLED`,`SORT`) values (1,'加班','',0,'2018-09-27 10:40:35','2018-10-17 14:57:58',0,1),(2,'定期奖金','',0,'2018-09-27 10:40:35','2018-10-17 14:58:10',0,1),(3,'考勤绩效','',0,'2018-09-27 10:40:35','2018-10-17 14:58:12',0,1),(4,'其他工资收入','',0,'2018-09-27 10:40:35','2018-10-17 14:59:01',0,1),(5,'事假','',0,'2018-09-27 10:40:35','2018-10-17 14:58:36',0,1),(6,'病假','',0,'2018-09-27 10:40:35','2018-10-17 14:58:15',0,1),(7,'迟到早退','',0,'2018-09-27 10:40:35','2018-10-17 14:58:16',0,1),(8,'其他缺勤','',0,'2018-09-27 10:40:35','2018-10-17 14:58:18',0,1),(9,'旷工','',0,'2018-09-27 10:40:35','2018-10-17 14:58:19',0,1),(10,'离岗','',0,'2018-09-27 10:40:35','2018-10-17 14:58:20',0,1),(11,'罚款','',0,'2018-09-27 10:40:35','2018-10-17 14:59:17',0,1),(12,'其他处罚','',0,'2018-09-27 10:40:35','2018-10-17 14:59:19',0,1),(13,'薪资','',0,'2018-09-27 10:40:35','2018-10-17 14:59:21',0,1),(14,'薪资调整','',0,'2018-09-27 10:40:35','2018-10-17 14:59:22',0,1),(15,'薪资说明','',0,'2018-09-27 10:40:35','2018-10-17 14:59:24',0,1),(16,'非本公司工资','',0,'2018-10-11 13:36:12','2018-10-17 14:59:25',0,1),(17,'减除费用','',0,'2018-09-27 10:40:35','2018-10-17 14:59:27',0,1),(18,'病事假说明','',0,'2018-09-27 10:40:35','2018-10-17 14:59:41',0,1),(19,'处罚说明','',0,'2018-09-27 10:40:35','2018-10-17 14:59:30',0,1),(20,'全年一次性奖金补贴','',0,'2018-09-27 10:40:35','2018-10-17 14:59:31',0,1),(21,'解除劳动关系一次性补偿金','',0,'2018-10-11 13:41:35','2018-10-17 14:59:47',0,1),(22,'免税所得','',0,'2018-10-11 13:42:05','2018-10-17 14:59:49',0,1),(23,'工作年限','',0,'2018-10-11 13:46:33','2018-10-17 14:59:50',0,1),(24,'本地区上年度平均职工工资','',0,'2018-10-12 10:38:23','2018-10-17 14:59:51',0,1);

UNLOCK TABLES;

/*Table structure for table `t_attribute` */

DROP TABLE IF EXISTS `t_attribute`;

CREATE TABLE `t_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `TABLE_NAME` int(2) DEFAULT NULL COMMENT '关联哪张表',
  `ITEM_ID` bigint(20) DEFAULT NULL COMMENT '关联表的ID',
  `NAME` varchar(255) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8 COMMENT='公司人事模板定制项表';

/*Data for the table `t_attribute` */

LOCK TABLES `t_attribute` WRITE;

insert  into `t_attribute`(`ID`,`TABLE_NAME`,`ITEM_ID`,`NAME`) values (2,0,1,'综合所得'),(3,0,6,'工资薪金收入'),(4,0,7,'基本工资'),(5,0,2,'利息股息红利收入'),(6,0,8,'全年一次性奖金'),(7,0,10,'劳务收入'),(8,0,3,'财产转让所得'),(9,0,9,'解除关系一次性补偿'),(10,0,11,'稿酬收入'),(11,0,4,'财产租赁所得'),(12,0,12,'特许权使用费收入'),(13,0,5,'偶然所得'),(14,0,13,'其他所得'),(21,3,1,'子女教育支出'),(22,3,2,'继续教育支出'),(23,3,3,'住房贷款利息支出'),(24,3,4,'住房租金支出'),(25,3,5,'大病医疗支出'),(26,1,6,'其他'),(27,1,5,'远洋运输船员伙食补贴'),(28,1,4,'公职人员公务用车／通讯及其他补贴'),(29,1,3,'年金'),(30,1,2,'商业养老保险'),(31,1,1,'商业健康保险费'),(44,7,1,'加班'),(45,7,2,'定期奖金'),(46,7,3,'考勤绩效'),(47,7,5,'事假'),(48,7,6,'病假'),(49,7,7,'迟到早退'),(50,7,8,'其他缺勤'),(51,7,9,'旷工'),(52,7,10,'离岗'),(53,7,4,'其他工资收入'),(54,7,11,'罚款'),(55,7,12,'其他处罚'),(56,7,13,'薪资'),(57,7,14,'薪资调整'),(58,7,15,'薪资说明'),(59,7,16,'非本公司工资'),(60,7,17,'减除费用'),(61,7,19,'处罚说明'),(62,7,20,'全年一次性奖金补贴'),(63,7,18,'病事假说明'),(64,7,21,'解除劳动关系一次性补偿金'),(65,7,22,'免税所得'),(66,7,23,'工作年限'),(67,7,24,'本地区上年度平均职工工资'),(68,3,6,'养老支出');

UNLOCK TABLES;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
