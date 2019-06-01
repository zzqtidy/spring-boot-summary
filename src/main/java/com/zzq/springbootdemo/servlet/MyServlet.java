package com.zzq.springbootdemo.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-05-22
 * Time: 8:46
 */
@Slf4j
public class MyServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.getOutputStream().write("这里是servlet的请求".getBytes("UTF-8"));
        String requestURI = req.getRequestURI();
        String remoteAddr = req.getRemoteAddr();   //这个方法取客户端ip"不够好"
        String remoteHost = req.getRemoteHost();
        log.info("请求url=" + requestURI + ",客户端ip=" + remoteAddr+",远程主机="+remoteHost);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

}
