### sql更新跟踪记录

#### 2018-10-18 更新记录
  ALTER TABLE `geckodb`.`t_service_type`   
    DROP COLUMN `SORT`, 
    ADD COLUMN `SORT` INT(11) NULL  COMMENT '排序' AFTER `IS_TOP`;
####  2018-10-18 16:23
   添加 表 t_protocol  t_protocol.sql
  


