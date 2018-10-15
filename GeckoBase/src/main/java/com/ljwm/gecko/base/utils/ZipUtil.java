package com.ljwm.gecko.base.utils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Janiffy
 * @date 2018/10/15 13:08
 */
public class ZipUtil {

  public static ZipOutputStream zipFiles(List<File> srcfile, File zipfile) {
    byte[] buf = new byte[1024];
    ZipOutputStream out = null;
    try {
      // Create the ZIP file
      out = new ZipOutputStream(new FileOutputStream(zipfile));
      // Compress the files
      for (int i = 0; i < srcfile.size(); i++) {
        File file = srcfile.get(i);
        FileInputStream in = new FileInputStream(file);
        // Add ZIP entry to output stream.
        out.putNextEntry(new ZipEntry(file.getName()));
        // Transfer bytes from the file to the ZIP file
        int len;
        while ((len = in.read(buf)) > 0) {
          out.write(buf, 0, len);
        }
        // Complete the entry
        out.closeEntry();
        in.close();
      }
      // Complete the ZIP file
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return out;
  }
}
