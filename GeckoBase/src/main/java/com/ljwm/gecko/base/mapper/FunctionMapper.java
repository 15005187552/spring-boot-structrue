package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.Function;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.dto.FunctionDto;
import com.ljwm.gecko.base.model.vo.FunctionTree;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-08-27
 */
public interface FunctionMapper extends BaseMapper<Function> {

  @Select("  SELECT f.* FROM `t_function` f LEFT JOIN `t_role_function` rf ON rf.`FUNCTION_ID`=f.`ID` WHERE rf.`ROLE_ID`=#{id}")
  @ResultMap("FunctionDto")
  List<FunctionDto> findByRoleId(@Param("ID") String id);

  @Select("  SELECT f.* FROM `t_function` f LEFT JOIN `t_role_function` rf ON rf.`FUNCTION_ID`=f.`ID` WHERE rf.`ROLE_ID`=#{id}")
  @ResultMap("BaseResultMap")
  List<Function> findBaseByRoleId(@Param("ID") String id);

  @Select("SELECT * FROM `t_function` WHERE `ID`=#{id}")
  @ResultMap("BaseResultMap")
  Function findById(@Param("ID") String id);

  @Select("SELECT * FROM `t_function` WHERE `ID`=#{id}")
  @ResultMap("FunctionDto")
  FunctionDto findDtoById(@Param("id") String id);

  @Select("SELECT * FROM `t_function` WHERE `PARENT_ID`=#{id}")
  @ResultMap("BaseResultMap")
  List<Function> findChildrenByFunctionId(@Param("id") String id);

  List<FunctionTree> tree(@Param("text") String text);

  @Select("SELECT * FROM `t_function` WHERE `PARENT_ID` = #{id}")
  @ResultMap("FunctionTree")
  List<FunctionTree> findChildrenTreeByParentId(@Param("ID") String id);

  @Select("SELECT count(*) FROM `t_role_function` WHERE `FUNCTION_ID` = #{id}")
  @ResultType(value = java.lang.Integer.class)
  Integer relationExist(@Param("id") String id);
}
