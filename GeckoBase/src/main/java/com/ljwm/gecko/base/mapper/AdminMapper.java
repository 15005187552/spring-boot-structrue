package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.Function;
import com.ljwm.gecko.base.model.dto.AdminDto;
import com.ljwm.gecko.base.model.vo.AdminVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台管理用户表 Mapper 接口
 * </p>
 *
 * @author xixil
 * @since 2018-08-23
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

  @Select("SELECT * FROM `t_admin` WHERE `USERNAME` = #{username} LIMIT 0,1")
  @ResultMap("AdminDto")
  AdminDto login(String username);

  @Select("SELECT * FROM `t_admin` WHERE `USERNAME` = #{username} LIMIT 0,1")
  @ResultMap("BaseResultMap")
  Admin logins(String username);

  List<AdminVo> find(Page<AdminVo> page, @Param("disabled") Integer disabled, @Param("text") String text,@Param("asc") Boolean asc);

  @Insert("INSERT INTO `t_admin` ( `ID`,`USERNAME`,`PASSWORD`,`NICK_NAME`,`CREATE_TIME`,`UPDATE_TIME`,`DISABLED` ) VALUES ( #{id},#{username},#{password},#{nickName},#{createTime},#{updateTime},#{disabled} )")
  Integer insertAll(Admin admin);
}
