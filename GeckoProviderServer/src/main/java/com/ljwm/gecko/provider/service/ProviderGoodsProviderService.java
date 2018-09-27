package com.ljwm.gecko.provider.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.entity.ProviderGoods;
import com.ljwm.gecko.base.entity.ProviderGoodsPath;
import com.ljwm.gecko.base.mapper.ProviderGoodsMapper;
import com.ljwm.gecko.base.mapper.ProviderGoodsPathMapper;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.model.vo.ProviderGoodsVo;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.provider.model.form.ProviderGoodsForm;
import com.ljwm.gecko.provider.model.form.ProviderGoodsQueryForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class ProviderGoodsProviderService {

  @Autowired
  private ProviderGoodsMapper providerGoodsMapper;

  @Autowired
  private ProviderGoodsPathMapper providerGoodsPathMapper;

  @Autowired
  private CommonService commonService;

  @Autowired
  private AppInfo appInfo;


  @Transactional
  public void save(ProviderGoodsForm providerGoodsForm){
    //更新操作
    if (providerGoodsForm.getId()!=null){
      ProviderGoods providerGoods = providerGoodsMapper.selectById(providerGoodsForm.getId());
      if (providerGoods==null){
        log.info("商品id{},查询不到该商品信息!",providerGoodsForm.getId());
        throw new LogicException(ResultEnum.DATA_ERROR,"查询不到该商品信息!");
      }
      BeanUtil.copyProperties(providerGoodsForm,providerGoods);
      if (StringUtils.isNotEmpty(providerGoodsForm.getMainImage())){
        if (!providerGoodsForm.getMainImage().contains(Constant.PROVIDER)){
          File file = new File(appInfo.getFilePath() + Constant.PROVIDER);
          if (!file.exists()) {
            file.mkdirs();
          }
          String srcPath = appInfo.getFilePath() + Constant.CACHE + providerGoodsForm.getMainImage();
          String destDir = appInfo.getFilePath() + Constant.PROVIDER+ "/";
          Fileutil.cutGeneralFile(srcPath, destDir);
          providerGoods.setMainImage(Constant.PROVIDER+ "/" +providerGoodsForm.getMainImage());
        }else {
          providerGoods.setMainImage(Constant.PROVIDER+ "/" +providerGoodsForm.getMainImage());
        }
      }
      List<String> subImagesList = Lists.newArrayList();
      if (CollectionUtils.isNotEmpty(providerGoodsForm.getSubImagesList())){
          for (String subImageName: providerGoodsForm.getSubImagesList()){
            if (subImageName.contains(Constant.PROVIDER)){
              subImagesList.add(subImageName);
            }else {
              File file = new File(appInfo.getFilePath() + Constant.PROVIDER);
              if (!file.exists()) {
                file.mkdirs();
              }
              String srcPath = appInfo.getFilePath() + Constant.CACHE + subImageName;
              String destDir = appInfo.getFilePath() + Constant.PROVIDER+ "/";
              Fileutil.cutGeneralFile(srcPath, destDir);
              subImagesList.add(Constant.PROVIDER+ "/" +subImageName);
            }
          }
      }
      providerGoodsPathMapper.deleteByGoodId(providerGoods.getId());
      for (String subImageName : subImagesList){
        ProviderGoodsPath providerGoodsPath = new ProviderGoodsPath();
        providerGoodsPath.setCreateTime(DateUtil.date());
        providerGoodsPath.setGoodId(providerGoods.getId());
        providerGoodsPath.setPicPath(subImageName);
        providerGoodsPath.setUpdateTime(DateUtil.date());
        providerGoodsPathMapper.insert(providerGoodsPath);
      }
    }else {
      ProviderGoods providerGoods = new ProviderGoods();
      BeanUtil.copyProperties(providerGoodsForm,providerGoods);
      if (StringUtils.isNotEmpty(providerGoodsForm.getMainImage())){
        File file = new File(appInfo.getFilePath() + Constant.PROVIDER);
        if (!file.exists()) {
          file.mkdirs();
        }
        String srcPath = appInfo.getFilePath() + Constant.CACHE + providerGoodsForm.getMainImage();
        String destDir = appInfo.getFilePath() + Constant.PROVIDER+ "/";
        Fileutil.cutGeneralFile(srcPath, destDir);
        providerGoods.setMainImage(Constant.PROVIDER+ "/" +providerGoodsForm.getMainImage());
      }
      List<String> subImagesList = Lists.newArrayList();
      if (CollectionUtils.isNotEmpty(providerGoodsForm.getSubImagesList())){
        for (String subImageName: providerGoodsForm.getSubImagesList()){
            File file = new File(appInfo.getFilePath() + Constant.PROVIDER);
            if (!file.exists()) {
              file.mkdirs();
            }
            String srcPath = appInfo.getFilePath() + Constant.CACHE + subImageName;
            String destDir = appInfo.getFilePath() + Constant.PROVIDER+ "/";
            Fileutil.cutGeneralFile(srcPath, destDir);
            subImagesList.add(Constant.PROVIDER+ "/" +subImageName);
        }
      }
      providerGoodsMapper.insert(providerGoods);
      for (String subImageName : subImagesList){
        ProviderGoodsPath providerGoodsPath = new ProviderGoodsPath();
        providerGoodsPath.setCreateTime(DateUtil.date());
        providerGoodsPath.setGoodId(providerGoods.getId());
        providerGoodsPath.setPicPath(subImageName);
        providerGoodsPath.setUpdateTime(DateUtil.date());
        providerGoodsPathMapper.insert(providerGoodsPath);
      }
    }
  }

  public Page<ProviderGoodsVo> findByPage(ProviderGoodsQueryForm providerGoodsQueryForm){
    return commonService.find(providerGoodsQueryForm, (p, q) -> providerGoodsMapper.findByPage(p, BeanUtil.beanToMap(providerGoodsQueryForm)));
  }
}
