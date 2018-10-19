package com.ljwm.gecko.admin.neditor;

import com.baidu.ueditor.UeditorConfigKit;
import com.baidu.ueditor.manager.AbstractFileManager;

/**
 * Created by yunqisong on 2017/12/01.
 */
public class MyNeditorConfigKit extends UeditorConfigKit {
  // 就为了得到父类的方法

  public static AbstractFileManager fileManager = new FileManager();

  public static void setFileManager(AbstractFileManager fileManager) {
    UeditorConfigKit.fileManager = fileManager;
    UeditorConfigKit.setFileManager(fileManager);
  }

  public static AbstractFileManager getFileManager() {
    return UeditorConfigKit.fileManager;
  }
}
