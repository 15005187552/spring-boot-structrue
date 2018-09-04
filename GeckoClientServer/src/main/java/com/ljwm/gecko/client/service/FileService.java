package com.ljwm.gecko.client.service;

import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.gecko.base.utils.FileKit;
import com.ljwm.gecko.client.model.ApplicationInfo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.ljwm.bootbase.dto.Result.fail;

/**
 * @author Janiffy
 * @date 2018/9/4 15:16
 */
@Service
public class FileService {
  @Autowired
  ApplicationInfo appInfo;

  public Object upload(MultipartFile file) {
    String filePath = FileKit.saveUploadFile(file, appInfo.getFilePath());
    if (StrUtil.isEmpty(filePath))
      return fail(ResultEnum.FAIL_TO_SAVE_FILE);
    Map<String, Object> map = new HashedMap();
    map.put("fileName", filePath);
    return map;
  }
}
