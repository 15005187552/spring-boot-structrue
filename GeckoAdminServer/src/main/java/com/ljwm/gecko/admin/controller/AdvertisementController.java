package com.ljwm.gecko.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.entity.Advertisement;
import com.ljwm.gecko.base.enums.EquipTypeEnum;
import com.ljwm.gecko.base.model.form.AdvertisementForm;
import com.ljwm.gecko.base.model.form.AdvertisementQuery;
import com.ljwm.gecko.base.model.vo.AdvertisementVo;
import com.ljwm.gecko.base.service.AdvertisementService;
import com.ljwm.gecko.base.utils.EnumUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("advertisement")
@Api(tags = "广告管理 API")
public class AdvertisementController extends BaseController {

  @Autowired
  private AdvertisementService advertisementService;

  @PostMapping("findAD")
  @ApiOperation("广告分页查询")
  public Result<Page<AdvertisementVo>> findAD(@RequestBody AdvertisementQuery query) {
    return success(advertisementService.find(query));
  }

  @PostMapping("saveAD")
  @ApiOperation("广告保存")
  public Result<Advertisement> saveAD(@RequestBody AdvertisementForm form) {
    return success(advertisementService.save(form));
  }

  @GetMapping("deleteAD/{id}")
  @ApiOperation("广告删除")
  public Result deleteAD(@PathVariable Long id) {
    advertisementService.delete(id);
    return success();
  }
  @GetMapping("disabledAD/{id}")
  @ApiOperation("广告禁用启用")
  public Result disabledAD(@PathVariable Long id) {
    advertisementService.adDisabeld(id);
    return success();
  }

  @GetMapping("getEquipType")
  public Result getEquipType() {
    return  success(EnumUtil.getStringEnumMap(EquipTypeEnum.class));
  }
}
