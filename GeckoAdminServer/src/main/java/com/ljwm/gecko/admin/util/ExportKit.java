package com.ljwm.gecko.admin.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.exception.LogicException;
import lombok.Cleanup;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Excel导出工具
 * Created by xuxiaoliang on 2018/7/10.
 */
@SuppressWarnings("ALL")
public class ExportKit {

  public static void exportExcel(List<?> list,
                                 String title,
                                 String sheetName,
                                 Class<?> pojoClass,
                                 String fileName,
                                 boolean isCreateHeader,
                                 HttpServletResponse response) {
    ExportParams exportParams = new ExportParams(title, sheetName);
    exportParams.setCreateHeadRows(isCreateHeader);
    defaultExport(list, pojoClass, fileName, response, exportParams);
  }

  public static void exportExcel(List<?> list,
                                 String title,
                                 String sheetName,
                                 Class<?> pojoClass,
                                 String fileName,
                                 HttpServletResponse response) {
    defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
  }

  public static void exportExcel(List<Map<String, Object>> list,
                                 String fileName,
                                 HttpServletResponse response) {
    defaultExport(list, fileName, response);
  }

  public static void defaultExport(List<?> list,
                                   Class<?> pojoClass,
                                   String fileName,
                                   HttpServletResponse response,
                                   ExportParams exportParams) {
    Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
    if (workbook != null) ;
    downLoadExcel(fileName, response, workbook);
  }

  public static void downLoadExcel(String fileName,
                                   HttpServletResponse response,
                                   Workbook workbook) {
    try {
      response.setCharacterEncoding("UTF-8");
      response.setHeader("content-Type", "application/vnd.ms-excel");
      response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
      workbook.write(response.getOutputStream());
    } catch (IOException e) {
      throw new LogicException(e.getMessage());
    }
  }

  public static void saveLocalExcel(List<?> list,
                                    Class<?> pojoClass,
                                    String fileName,
                                    String filePath,
                                    ExportParams exportParams) {
    Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
    if (workbook != null) ;
    saveLocalExcel(fileName, filePath, workbook);
  }

  public static void saveLocalExcel(String fileName,
                                    String filePath,
                                    Workbook workbook) {
    try {
      File dir = new File(filePath);
      if (!dir.exists()) dir.mkdirs();
      File file = new File(filePath + (filePath.endsWith(File.separator) ? "" : File.separator) + fileName);
      if (!file.exists()) file.createNewFile();
      @Cleanup
      OutputStream outputStream = FileUtil.getOutputStream(file);
      workbook.write(outputStream);
    } catch (IOException e) {
      throw new LogicException(e.getMessage());
    }
  }

  public static void defaultExport(List<Map<String, Object>> list,
                                   String fileName,
                                   HttpServletResponse response) {
    Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
    if (workbook != null) ;
    downLoadExcel(fileName, response, workbook);
  }

  public static <T> List<T> importExcel(String filePath,
                                        Integer titleRows,
                                        Integer headerRows,
                                        Class<T> pojoClass) {
    if (StrUtil.isBlank(filePath)) {
      return null;
    }
    ImportParams params = new ImportParams();
    params.setTitleRows(titleRows);
    params.setHeadRows(headerRows);
    List<T> list = null;
    try {
      list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
    } catch (NoSuchElementException e) {
      throw new LogicException("模板不能为空");
    } catch (Exception e) {
      e.printStackTrace();
      throw new LogicException(e.getMessage());
    }
    return list;
  }

  public static <T> List<T> importExcel(MultipartFile file,
                                        Integer titleRows,
                                        Integer headerRows,
                                        Class<T> pojoClass) {
    if (file == null) {
      return null;
    }
    ImportParams params = new ImportParams();
    params.setTitleRows(titleRows);
    params.setHeadRows(headerRows);
    List<T> list = null;
    try {
      list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
    } catch (NoSuchElementException e) {
      throw new LogicException("excel文件不能为空");
    } catch (Exception e) {
      throw new LogicException(e.getMessage());
    }
    return list;
  }

}
