package com.ljwm.gecko.client.service;

import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.utils.FileKit;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.ljwm.bootbase.dto.Result.fail;
import static com.ljwm.bootbase.dto.Result.success;

/**
 * @author Janiffy
 * @date 2018/9/4 15:16
 */
@Service
public class FileService {
  @Autowired
  ApplicationInfo appInfo;

  public Result upload(MultipartFile file) {
    String filePath = FileKit.saveUploadFile(file, appInfo.getFilePath()+ Constant.CACHE);
    if (StrUtil.isEmpty(filePath))
      return fail(ResultEnum.FAIL_TO_SAVE_FILE);
    Map<String, Object> map = new HashedMap();
    map.put("fileName", filePath);
    return success(map);
  }
}
