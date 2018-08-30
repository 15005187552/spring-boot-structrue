package com.ljwm.gecko.client.controller;

import com.ljwm.bootbase.controller.BaseController;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.enums.EquipTypeEnum;
import com.ljwm.gecko.base.model.dto.ClientAdvertisementDto;
import com.ljwm.gecko.base.model.vo.AdvertisementVo;
import com.ljwm.gecko.base.service.AdvertisementService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/advertisement")
public class AdvertisementController extends BaseController {

  @Autowired
  private AdvertisementService advertisementService;

  @GetMapping("findClient")
  @ApiOperation(value = "查询客户端广告列表")
  public Result<List<AdvertisementVo>> findClient(){
    ClientAdvertisementDto clientAdvertisementDto = new ClientAdvertisementDto();
    clientAdvertisementDto.setDisabled(DisabledEnum.ENABLED.getCode());
    clientAdvertisementDto.setEquipType(EquipTypeEnum.WEIXIN.getCode());
    return success(advertisementService.findClient(clientAdvertisementDto));
  }

}
