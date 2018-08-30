package com.ljwm.gecko.base.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.ljwm.gecko.base.entity.MobileCode;
import com.ljwm.gecko.base.mapper.MobileCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/8/29 15:47
 */
@Repository
public class MobileCodeDao {

  @Autowired
  MobileCodeMapper mobileCodeMapper;

  public MobileCode find(String phoneNum) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("MOBILE", phoneNum);
    List<MobileCode> list = mobileCodeMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)) {
      return mobileCodeMapper.selectByMap(map).get(0);
    }
    return null;
  }

  public void update(MobileCode updateMobile) {
    mobileCodeMapper.updateById(updateMobile);
  }

  public void insert(MobileCode insertMobile) {
    mobileCodeMapper.insert(insertMobile);
  }

  public MobileCode select(String checkCode, String phoneNum) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("MOBILE", phoneNum);
    map.put("CODE", checkCode);
    List<MobileCode> list = mobileCodeMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)) {
      return mobileCodeMapper.selectByMap(map).get(0);
    }
    return null;
  }
}
