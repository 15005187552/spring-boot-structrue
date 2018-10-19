package com.ljwm.gecko.admin.controller;

import com.ljwm.gecko.admin.neditor.NEditorActionEnter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yunqisong on 2017/12/01.
 */
@Slf4j
@Component
@WebServlet(name = "UEditorServlet", urlPatterns = "/UEditor")
public class NEditorController extends HttpServlet {

  @Value("${appInfo.neditorEnv}")
  private String env;

  @Value("${appInfo.filePath}")
  private String filePath;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("utf-8");
    response.setHeader("Content-Type", "text/html");
    PrintWriter out = response.getWriter();
    String content = NEditorActionEnter.me(env, filePath).exec(request);
    out.write(content);
  }

}
