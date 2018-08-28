package com.ljwm.gecko.provider.controller;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.entity.Location;
import com.ljwm.gecko.base.service.LocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.ljwm.bootbase.dto.Result.success;

/**
 * @author Janiffy
 * @date 2018/8/24 9:49
 */
@Slf4j
@RestController
@RequestMapping("/location")
@Api(tags = "地理位置 API")
public class LocationController {

  @Autowired
  LocationService locationService;

  @PostMapping("/updateLocation")
  @ApiOperation("导入省市区txt文件，前端不需要调试该接口")
  public void updateLocation() throws IOException {
    File file = new ClassPathResource("list.txt").getFile();
    StringBuilder result = new StringBuilder();
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
      String s = null;
      while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
        //result.append(System.lineSeparator()+s);
        String[] str = s.split("\"");
        Location location = new Location();
        location.setCode(Integer.valueOf(str[1]));
        location.setName(str[3]);
        locationService.insertOrUpdate(location);
      }
      br.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  @PostMapping("/getProvince")
  @ApiOperation("获取所有省或者直辖市")
  public Result getProvince(){
    return success(locationService.getProvince());
  }

  @PostMapping("/getCityOrArea")
  @ApiOperation("根据联动获取市区")
  public  Result getCityOrArea(@RequestParam Integer code){
    return success(locationService.getCityOrArea(code));
  }

}
