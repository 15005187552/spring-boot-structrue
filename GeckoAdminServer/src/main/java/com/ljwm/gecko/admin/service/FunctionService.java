package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.bean.Dict;
import com.ljwm.gecko.admin.model.form.FunctionSaveForm;
import com.ljwm.gecko.base.entity.Function;
import com.ljwm.gecko.base.mapper.FunctionMapper;
import com.ljwm.gecko.base.model.vo.FunctionTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@SuppressWarnings("all")
public class FunctionService {

  @Autowired
  private Dict dict;

  @Autowired
  private CommonService commonService;

  @Autowired
  private FunctionMapper functionMapper;

  @Transactional
  public void init() {
    for (String menu : dict.getBuiltInMenu()) {
      String[] menuInfos = menu.split(":");
      Function function = new Function()
        .setParentId(Integer.valueOf(menuInfos[6]))
        .setId(menuInfos[0])
        .setName(menuInfos[1])
        .setTitle(menuInfos[2])
        .setDescription(menuInfos[3])
        .setUrl(menuInfos[4])
        .setIcon(menuInfos[5]);
//      functionMapper.insert(function);
      commonService.insertOrUpdate(function, functionMapper);
    }
  }


  public List<FunctionTree> tree(String text) {
    return functionMapper.tree(text);
  }


  public Function saveFunction(FunctionSaveForm form) {
    Function function = null;
    if (StrUtil.isBlank(form.getId()))
      function = functionMapper.selectById(form.getId());
    else
      function = new Function();

    BeanUtil.copyProperties(form, function);

    commonService.insertOrUpdate(function, functionMapper);
    return function;
  }
}
