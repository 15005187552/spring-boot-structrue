package com.ljwm.gecko.admin.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.bean.LocationExcelBean;
import com.ljwm.gecko.admin.model.form.LocationRateQuery;
import com.ljwm.gecko.admin.model.form.RateSaveForm;
import com.ljwm.gecko.admin.util.ExportKit;
import com.ljwm.gecko.base.entity.CityItem;
import com.ljwm.gecko.base.entity.Location;
import com.ljwm.gecko.base.entity.SpecialDeduction;
import com.ljwm.gecko.base.mapper.CityItemMapper;
import com.ljwm.gecko.base.mapper.LocationMapper;
import com.ljwm.gecko.base.mapper.SpecialDeductionMapper;
import com.ljwm.gecko.base.model.dto.LocationRateDetailDto;
import com.ljwm.gecko.base.model.dto.LocationRateDto;
import com.ljwm.gecko.base.model.vo.*;
import com.ljwm.gecko.base.service.CityItemService;
import com.ljwm.gecko.base.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings("all")
public class LocationRateService {

  @Autowired
  private LocationMapper locationMapper;

  @Autowired
  private CityItemMapper cityItemMapper;

  @Autowired
  private CityItemService cityItemService;

  @Autowired
  private LocationService locationService;

  @Autowired
  private CommonService commonService;

  @Autowired
  private SpecialDeductionMapper specialDeductionMapper;

  public void uploadLocationRate(MultipartFile multipartFile) {
    String fileName = null;
    if (!multipartFile.isEmpty()) {
      fileName = multipartFile.getOriginalFilename();
      log.debug("上传的文件名为：" + fileName);
      String suffixName = fileName.substring(fileName.lastIndexOf("."));
      log.debug("上传的后缀名为：" + suffixName);
      if (!suffixName.endsWith("xls") && !suffixName.endsWith("xls")) {
        log.error("上传文件不是XLS 或  XLSX类型");
        throw new LogicException(ResultEnum.DATA_ERROR, "上传文件格式不正确!");
      }
      try {
        InputStream inputStream = multipartFile.getInputStream();
        ExcelReader excelReader = ExcelUtil.getReader(inputStream);
        List<Sheet> sheetList = excelReader.getSheets();
        List<LocationRateDto> locationRateDtoList = Lists.newArrayList();
        for (Sheet sheet : sheetList) {
          log.info("城市：{}", sheet.getSheetName());
          LocationRateDto locationRateDto = new LocationRateDto();
          locationRateDto.setProvinceName(fileName.split("\\.")[0]);
          locationRateDto.setCityName(sheet.getSheetName());
          List<LocationRateDetailDto> locationRateDetailDtoList = Lists.newArrayList();
          BigDecimal lowerLimit = BigDecimal.ZERO;
          BigDecimal upperLimit = BigDecimal.ZERO;
          if (StringUtils.isNotEmpty(sheet.getRow(3).getCell(1).toString())) {
            lowerLimit = new BigDecimal(sheet.getRow(3).getCell(1).toString());
          }
          if (StringUtils.isNotEmpty(sheet.getRow(3).getCell(2).toString())) {
            upperLimit = new BigDecimal(sheet.getRow(3).getCell(2).toString());
          }
          for (int i = 3; i < 8; i++) {
            LocationRateDetailDto locationRateDetailDto = new LocationRateDetailDto();
            locationRateDetailDto.setPerType(0);
            locationRateDetailDto.setLowerLimit(lowerLimit);
            locationRateDetailDto.setUpperLimit(upperLimit);
            locationRateDetailDto.setName(sheet.getRow(i).getCell(0).toString());
            if (StringUtils.isNotEmpty(sheet.getRow(i).getCell(3).toString())) {
              locationRateDetailDto.setCompanyPer(new BigDecimal(sheet.getRow(i).getCell(3).toString()));
            } else {
              locationRateDetailDto.setCompanyPer(BigDecimal.ZERO);
            }
            if (StringUtils.isNotEmpty(sheet.getRow(i).getCell(4).toString())) {
              locationRateDetailDto.setPersonPer(new BigDecimal(sheet.getRow(i).getCell(4).toString()));
            } else {
              locationRateDetailDto.setPersonPer(BigDecimal.ZERO);
            }
            locationRateDetailDtoList.add(locationRateDetailDto);
          }
          int rowTempCount = sheet.getLastRowNum();
          int rowCount = 0;
          for (int j = 8; j < rowTempCount; j++) {
            if (StringUtils.isNotEmpty(sheet.getRow(j).getCell(0).toString()) && Objects.equals("公积金", sheet.getRow(j).getCell(0).toString())) {
              rowCount = j;
              break;
            }
          }
          LocationRateDetailDto locationRateDetailDto = new LocationRateDetailDto();
          if (StringUtils.isNotEmpty(sheet.getRow(rowCount).getCell(1).toString())) {
            locationRateDetailDto.setLowerLimit(new BigDecimal(sheet.getRow(rowCount).getCell(1).toString()));
          } else {
            locationRateDetailDto.setLowerLimit(BigDecimal.ZERO);
          }
          if (StringUtils.isNotEmpty(sheet.getRow(rowCount).getCell(2).toString())) {
            locationRateDetailDto.setUpperLimit(new BigDecimal(sheet.getRow(rowCount).getCell(2).toString()));
          } else {
            locationRateDetailDto.setUpperLimit(BigDecimal.ZERO);
          }
          locationRateDetailDto.setName(sheet.getRow(rowCount).getCell(0).toString());
          if (StringUtils.isNotEmpty(sheet.getRow(rowCount).getCell(3).toString())) {
            locationRateDetailDto.setCompanyPer(new BigDecimal(sheet.getRow(rowCount).getCell(3).toString()));
          } else {
            locationRateDetailDto.setCompanyPer(BigDecimal.ZERO);
          }
          if (StringUtils.isNotEmpty(sheet.getRow(rowCount).getCell(4).toString())) {
            locationRateDetailDto.setPersonPer(new BigDecimal(sheet.getRow(rowCount).getCell(4).toString()));
          } else {
            locationRateDetailDto.setPersonPer(BigDecimal.ZERO);
          }
          locationRateDetailDtoList.add(locationRateDetailDto);
          locationRateDto.setLocationRateDetailDtoList(locationRateDetailDtoList);
          locationRateDtoList.add(locationRateDto);
            /*
            log.info("名称：{} ,下限:{},上线:{},单位:{},个人:{}",sheet.getRow(3).getCell(0).toString(),sheet.getRow(3).getCell(1).toString(),sheet.getRow(3).getCell(2).toString(),sheet.getRow(3).getCell(3).toString(),sheet.getRow(3).getCell(4).toString());
            log.info("名称：{} ,下限:{},上线:{},单位:{},个人:{}",sheet.getRow(4).getCell(0).toString(),sheet.getRow(4).getCell(1).toString(),sheet.getRow(4).getCell(2).toString(),sheet.getRow(4).getCell(3).toString(),sheet.getRow(4).getCell(4).toString());
            log.info("名称：{} ,下限:{},上线:{},单位:{},个人:{}",sheet.getRow(5).getCell(0).toString(),sheet.getRow(5).getCell(1).toString(),sheet.getRow(5).getCell(2).toString(),sheet.getRow(5).getCell(3).toString(),sheet.getRow(5).getCell(4).toString());
            log.info("名称：{} ,下限:{},上线:{},单位:{},个人:{}",sheet.getRow(6).getCell(0).toString(),sheet.getRow(6).getCell(1).toString(),sheet.getRow(6).getCell(2).toString(),sheet.getRow(6).getCell(3).toString(),sheet.getRow(6).getCell(4).toString());
            log.info("名称：{} ,下限:{},上线:{},单位:{},个人:{}",sheet.getRow(7).getCell(0).toString(),sheet.getRow(7).getCell(1).toString(),sheet.getRow(7).getCell(2).toString(),sheet.getRow(7).getCell(3).toString(),sheet.getRow(7).getCell(4).toString());
            log.info("名称：{} ,下限:{},上线:{},单位:{},个人:{}",sheet.getRow(8).getCell(0).toString(),sheet.getRow(8).getCell(1).toString(),sheet.getRow(8).getCell(2).toString(),sheet.getRow(8).getCell(3).toString(),sheet.getRow(8).getCell(4).toString());
            log.info("名称：{} ,下限:{},上线:{},单位:{},个人:{}",sheet.getRow(12).getCell(0).toString(),sheet.getRow(12).getCell(1).toString(),sheet.getRow(12).getCell(2).toString(),sheet.getRow(12).getCell(3).toString(),sheet.getRow(12).getCell(4).toString());
            */
        }
        log.info("list:{}", locationRateDtoList.size());
        cityItemService.saveLocationRate(locationRateDtoList);
      } catch (IOException ioe) {
        log.error("读取IO流异常：{}", ioe);
        throw new LogicException(ResultEnum.DATA_ERROR, "读取流异常");
      }
    }
  }

  /**
   * 获取省份 以及 直辖市
   *
   * @return
   */
  public List<SimpleProv> findProv() {
    return locationService.getProvince().stream().map(SimpleProv::new).collect(Collectors.toList());
  }

  /**
   * 获取省份
   *
   * @return
   */
  public List<SimpleLocation> getProvAndCity() {
    return locationMapper.findProvAndCity();
  }

  /**
   * 分页获取服务
   *
   * @param query
   * @return
   */
  public Page<LocationRateVo> find(LocationRateQuery query) {
    // 处理判断类型
    String suffix = query.getCode().toString().substring(query.getCode().toString().length() - 4, query.getCode().toString().length());
    query.setCode(Objects.equals(suffix, "0000") ? Integer.valueOf(query.getCode().toString().substring(0, 2)) : query.getCode());
    // 分页获取数据
    Page<LocationRateVo> page = commonService.find(query, (p, q) -> locationMapper.find(p, BeanUtil.beanToMap(query)));
    // 处理省份
    page.setRecords(page.getRecords().stream().filter(i -> CollectionUtil.isNotEmpty(i.getRates())).collect(Collectors.toList()));
    return page;
  }

  @Transactional
  public CityItem saveRate(RateSaveForm saveForm) {
    CityItem cityItem = null;
    if (saveForm.getId() != null)
      cityItem = cityItemMapper.selectById(saveForm.getId());
    if (cityItem == null)
      cityItem = new CityItem();
    BeanUtil.copyProperties(saveForm, cityItem);
    commonService.insertOrUpdate(cityItem, cityItemMapper);
    return cityItem;
  }

  public Boolean deleteRate(Long id) {
    return Optional
      .ofNullable(id)
      .map(code -> {
        CityItem cityItem = cityItemMapper.selectById(code);
        if (cityItem == null) throw new LogicException(ResultEnum.DATA_ERROR, "未找到ID为" + code + "的税率值");
        cityItemMapper.deleteById(code);
        return true;
      }).get();
  }

  public List<SimpleSpecial> getItemType(Long regionId) {
    return Optional
      .ofNullable(regionId)
      .map(id -> {
        List<CityItem> cityItems = cityItemMapper.selectByMap(Kv.by("REGION_CODE", id));
        List<Long> itemIds = cityItems.stream().map(CityItem::getItemType).collect(Collectors.toList());
        List<SpecialDeduction> specialDeductions = specialDeductionMapper.selectList(null);
        return specialDeductions.stream().filter(i -> !itemIds.contains(i.getId())).map(SimpleSpecial::new).collect(Collectors.toList());
      }).get();
  }

  public void downloadByProv(HttpServletResponse response, Integer code) {

    List<Location> locations = locationMapper.findPCByCode(code);

    List<Map<String, Object>> sheetsList = new ArrayList<>();
    List<LocationExcelBean> data = null;

    for (Location location : locations) {
      List<RateVo> rates = cityItemMapper.findByLoCode(Integer.valueOf(location.getCode()));
      if (CollectionUtil.isNotEmpty(rates)) {
        data = rates.stream().map(LocationExcelBean::new).collect(Collectors.toList());
        ExportParams params = new ExportParams();
        params.setSheetName(location.getName());
        params.setTitle("职工社保公积金缴费比例");
        sheetsList.add(Kv.by("data", data).set("title", params).set("entity", LocationExcelBean.class));
      }
    }
    Workbook workbook = ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);
    String fileName = locations.stream().filter(i -> Objects.equals(i.getCode(), code)).findFirst().map(i -> i.getName()).get();
    ExportKit.downLoadExcel(fileName + ".xls", response, workbook);
  }
}
