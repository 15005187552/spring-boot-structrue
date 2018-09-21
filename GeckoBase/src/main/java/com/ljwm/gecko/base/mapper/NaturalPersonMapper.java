package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.NaturalPerson;
import com.ljwm.gecko.base.model.dto.NaturalPersonDto;
import com.ljwm.gecko.base.model.vo.NaturalPersonVo;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 自然人纳税基本信息表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-05
 */
@Repository
public interface NaturalPersonMapper extends BaseMapper<NaturalPerson> {

  @ResultMap("NaturalPersonDto")
  @Select("SELECT a.*, b.REG_MOBILE FROM t_natural_person a LEFT JOIN t_member b ON a.COMPANY_ID = #{companyId} AND a.MEMBER_ID = b.ID")
  List<NaturalPersonDto> selectByCompanyId(Long companyId);


  @Select("SELECT * FROM `t_natural_person` WHERE `MEMBER_ID` = #{id}")
  @ResultMap("NaturalPersonVo")
  NaturalPersonVo findById(Long id);
}
