package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Function;
import com.ljwm.gecko.base.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.model.dto.RoleDto;
import com.ljwm.gecko.base.model.vo.RoleVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-08-27
 */
public interface RoleMapper extends BaseMapper<Role> {

  @Select("  SELECT r.* FROM `t_role` r LEFT JOIN `t_admin_role` ar ON ar.`ROLE_ID`=r.`ID` WHERE ar.`ADMIN_ID`=#{id}")
  @ResultMap("RoleDto")
  List<RoleDto> findByUserId(@Param("ID") Long id);

  @Select("  SELECT r.* FROM `t_role` r LEFT JOIN `t_admin_role` ar ON ar.`ROLE_ID`=r.`ID` WHERE ar.`ADMIN_ID`=#{id}")
  @ResultMap("BaseResultMap")
  List<Role> findBaseByUserId(@Param("ID") Long id);

  List<RoleVo> find(Page<RoleVo> page,@Param("param") Map map);

  List<RoleVo> find(@Param("text") String text, @Param("disabled") Integer disabled);

  @Select("SELECT count(*) FROM `t_admin_role` WHERE `ROLE_ID` = #{id}")
  @ResultType(value = java.lang.Integer.class)
  Integer relationExist(@Param("id") Long id);

  Boolean deleteAble(Long id);

  @Insert("INSERT INTO `t_role` ( `ID`,`ROLE_NAME`,`ROLE_DESC`,`DISABLED`) VALUES ( #{id},#{roleName},#{roleDesc},#{disabled})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID")
  Integer insertAll(Role role);
}
