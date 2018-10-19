package com.ljwm.gecko.admin.neditor;

import cn.hutool.core.io.FileUtil;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.manager.DefaultFileManager;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * Created by yunqisong on 2017/12/01.
 */
public class FileManager extends DefaultFileManager {


  @Override
  public State saveFile(byte[] data, String rootPath, String savePath) {
    File file = new File(rootPath + savePath);

    State state = valid(file);
    if (!state.isSuccess()) {
      return state;
    }

    try {
      FileUtils.writeByteArrayToFile(file, data);
      File fNew = new File("/www" + file.getAbsolutePath().split("www")[1]);
      if (!fNew.getParentFile().exists()) fNew.getParentFile().mkdirs();
      file.createNewFile();
    } catch (IOException ioe) {
      return new BaseState(false, AppInfo.IO_ERROR);
    }

    state = new BaseState(true, file.getAbsolutePath());
    state.putInfo("size", data.length);
    state.putInfo("title", file.getName());
    return state;
  }

  @Override
  public State saveFile(InputStream is, String rootPath, String savePath) {
    byte[] dataBuf = new byte[BUFFER_SIZE];
    BufferedInputStream bis = new BufferedInputStream(is, BUFFER_SIZE);

    State state = null;
    try {
      File tmpFile = getTmpFile();
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile), BUFFER_SIZE);

      int count = 0;
      while ((count = bis.read(dataBuf)) != -1) {
        bos.write(dataBuf, 0, count);
      }
      bos.flush();
      bos.close();

      state = saveTmpFile(tmpFile, rootPath + savePath);

      if (!state.isSuccess()) {
        tmpFile.delete();
      }

      return state;
    } catch (IOException e) {
      // ignore
    }
    return new BaseState(false, AppInfo.IO_ERROR);
  }

  @Override
  public State saveFile(InputStream is, String rootPath, String savePath, long maxSize) {
    byte[] dataBuf = new byte[BUFFER_SIZE];
    BufferedInputStream bis = new BufferedInputStream(is, BUFFER_SIZE);

    try {
      File tmpFile = getTmpFile();
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile), BUFFER_SIZE);

      int count = 0;
      while ((count = bis.read(dataBuf)) != -1) {
        bos.write(dataBuf, 0, count);
      }
      bos.flush();
      bos.close();

      if (tmpFile.length() > maxSize) {
        tmpFile.delete();
        return new BaseState(false, AppInfo.MAX_SIZE);
      }

      State state = saveTmpFile(tmpFile, rootPath + savePath);

      if (!state.isSuccess()) {
        tmpFile.delete();
      }

      return state;

    } catch (IOException e) {
      // ignore
    }
    return new BaseState(false, AppInfo.IO_ERROR);
  }

  private static File getTmpFile() throws IOException {
    File tmpDir = FileUtils.getTempDirectory();
    if (!tmpDir.exists()) {
      FileUtils.forceMkdir(tmpDir);
    }
    String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
    return new File(tmpDir, tmpFileName);
  }


  private static State saveTmpFile(File tmpFile, String path) {
    File targetFile = new File(path);

    if (targetFile.canWrite()) {
      return new BaseState(false, AppInfo.PERMISSION_DENIED);
    }
    try {
      FileUtils.moveFile(tmpFile, targetFile);
      File file = new File("/www" + targetFile.getAbsolutePath().split("www")[1]);
      if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
      file.createNewFile();
      FileUtil.move(targetFile, file, true);
    } catch (IOException e) {
      return new BaseState(false, AppInfo.IO_ERROR);
    }

    State state = new BaseState(true);
    state.putInfo("size", targetFile.length());
    state.putInfo("title", targetFile.getName());

    return state;
  }

  private static State valid(File file) {
    File parentPath = file.getParentFile();

    if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
      return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
    }

    if (!parentPath.canWrite()) {
      return new BaseState(false, AppInfo.PERMISSION_DENIED);
    }

    return new BaseState(true);
  }

}
