### sql更新跟踪记录

<<<<<<< Updated upstream
#### 2018-10-18 更新记录
  ALTER TABLE `geckodb`.`t_service_type`   
    DROP COLUMN `SORT`, 
    ADD COLUMN `SORT` INT(11) NULL  COMMENT '排序' AFTER `IS_TOP`;
####  2018-10-18 16:23
   添加 表 t_protocol  t_protocol.sql
  
ALTER TABLE `t_mobile_code`
MODIFY COLUMN `TYPE`  int(2) NOT NULL COMMENT '短信模板类型' AFTER `CODE`ALTER TABLE `t_mobile_code`,
MODIFY COLUMN `CREATE_TIME`  varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `FROM_IP`,
MODIFY COLUMN `UPDATE_TIME`  varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `CREATE_TIME`;


