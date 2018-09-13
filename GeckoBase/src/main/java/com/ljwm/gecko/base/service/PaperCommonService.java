package com.ljwm.gecko.base.service;

import com.ljwm.gecko.base.entity.Paper;
import com.ljwm.gecko.base.mapper.PaperMapper;
import com.ljwm.gecko.base.model.vo.PaperVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.List;

@Service
@SuppressWarnings("all")
public class PaperCommonService {

  @Autowired
  private PaperMapper paperMapper;

  public List<Paper> getAll() {
    return paperMapper.selectList(null);
  }
}
