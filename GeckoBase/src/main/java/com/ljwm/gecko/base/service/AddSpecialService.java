package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.AddSpecial;
import com.ljwm.gecko.base.mapper.AddSpecialMapper;
import com.ljwm.gecko.base.model.dto.AddSpecialDto;
import com.ljwm.gecko.base.model.dto.AddSpecialQueryDto;
import com.ljwm.gecko.base.model.vo.AddSpecialVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AddSpecialService {

  @Autowired
  private AddSpecialMapper addSpecialMapper;

  @Autowired
  private CommonService commonService;

  @Transactional
  public AddSpecialVo save(AddSpecialDto addSpecialDto) {
    return Optional
      .ofNullable(addSpecialDto)
      .map(f -> {
        AddSpecial addSpecial = new AddSpecial();
        BeanUtil.copyProperties(f, addSpecial);
        if (f.getId() != null) {
          addSpecialMapper.updateById(addSpecial);
        } else {
          addSpecialMapper.insert(addSpecial);
        }
        return new AddSpecialVo(addSpecial);
      }).map(AddSpecialVo::new).get();
  }

  public List<AddSpecialVo> find() {
    return addSpecialMapper.find();
  }

  public Page<AddSpecialVo> findPage(AddSpecialQueryDto addSpecialQueryDto) {
    return commonService.find(addSpecialQueryDto, (p, q) -> addSpecialMapper.findPage(p, Kv.by("text", addSpecialQueryDto.getText())));
  }

  @Transactional
  public void delete(Long id) {
    if (!addSpecialMapper.deleteAble(id)) {
      throw new LogicException(ResultEnum.DATA_ERROR, "该节点已经被使用,无法删除");
    }
    addSpecialMapper.deleteById(id);
  }
}
