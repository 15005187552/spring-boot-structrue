package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljwm.gecko.base.entity.Attribute;
import com.ljwm.gecko.base.model.vo.AttributeCompanyVo;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 公司人事模板定制项表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-20
 */
@Repository
public interface AttributeMapper extends BaseMapper<Attribute> {

  @Select("SELECT * FROM t_attribute b LEFT JOIN t_template a\n" +
    "ON a.COMPANY_ID = #{companyId} AND a.ATTRIBUTE_ID = b.ID")
  @ResultMap("ResultMap")
  List<AttributeCompanyVo> selectAllAttribute(Long companyId);


}
