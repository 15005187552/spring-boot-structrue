package com.ljwm.gecko.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.gecko.base.entity.Tax;
import com.ljwm.gecko.base.model.vo.NaturalPersonTaxVo;
import com.ljwm.gecko.base.model.vo.TaxListVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 报税数据主表 Mapper 接口
 * </p>
 *
 * @author Levis
 * @since 2018-09-06
 */
@Component
public interface TaxMapper extends BaseMapper<Tax> {

  @Select("SELECT * FROM `t_tax` WHERE MEMBER_ID = #{map.memberId} AND DECLARE_TYPE= #{map.declareType}")
  List<Tax> selectByPage(@Param("taxPage") Page<Tax> taxPage, @Param("map") Map map);


  @ResultMap("NaturalResultMap")
  List<NaturalPersonTaxVo> selectTaxByList(Page<NaturalPersonTaxVo> naturalPersonTaxVoPage, @Param("map")Map map);


  @Select("SELECT * FROM t_tax a, t_natural_person b WHERE a.MEMBER_ID = b.MEMBER_ID AND a.ID = #{taxId}")
  @ResultMap("NaturalResultMap")
  List<NaturalPersonTaxVo> selectTaxInfo(Long taxId);

  List<Tax> selectTaxList(@Param("page") Page page, @Param("map") Map<String, Object> map);

  @ResultMap("TaxInfoMap")
  List<TaxListVo> selectTaxVoList(@Param("page") Page page, @Param("map") Map<String, Object> map);

  /*@Select("SELECT * FROM t_natural_person a, t_tax b WHERE a.COMPANY_ID = #{map.companyId} AND b.MEMBER_ID = a.MEMBER_ID <if test=\"map.name!=null and map.name != ''\">AND a.`NAME` = #{map.name}</if>\n" +
    "<if test=\"map.declareTime!=null and map.declareTime != ''\">AND b.DECLARE_TIME = #{map.declareTime}</if>")
  List<NaturalPersonTaxVo> selectTaxByList(@Param("page") Page<NaturalPersonTaxVo> page, @Param("map")Map<String, Object> map);*/

}
