package com.ljwm.gecko.base.mapper;

import com.ljwm.gecko.base.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.dto.RoleDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-08-27
 */
public interface RoleMapper extends BaseMapper<Role> {

  @Select("  SELECT r.* FROM `t_role` r LEFT JOIN `t_admin_role` ar ON ar.`ROLE_ID`=r.`ID` WHERE ar.`ADMIN_ID`=#{id}")
  @ResultMap("RoleDto")
  List<RoleDto> findByUserId(@Param("ID") String id);

  @Select("  SELECT r.* FROM `t_role` r LEFT JOIN `t_admin_role` ar ON ar.`ROLE_ID`=r.`ID` WHERE ar.`ADMIN_ID`=#{id}")
  @ResultMap("BaseResultMap")
  List<Role> findBaseByUserId(@Param("ID") String id);
}
