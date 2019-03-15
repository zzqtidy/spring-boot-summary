package com.zzq.springbootdemo.util;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-05
 */
public class Base64CoderCCTest {
    @Test
    public void base64Coder() throws UnsupportedEncodingException {
        /********************测试一般encode*********************/
        String data = "找一个好姑娘做老婆是我的梦 想！";
        System.out.println("原文-->"+data);
        String encodedStr = Base64CoderCC.encode(data);
        System.out.println("加密后-->"+encodedStr);
        String decodedStr = Base64CoderCC.decode(encodedStr);
        System.out.println("解密后-->"+decodedStr);
        System.out.println(data.equals(decodedStr));
        System.out.println("================================");
        /********************测试安全encode*********************/
        String data2 = "找一个好姑娘做老婆是我的梦 想！找一个好姑娘做老婆是我的梦 想！";
        System.out.println("原文-->"+data2);
        String encodedStr2 = Base64CoderCC.encodeSafe(data2);
        System.out.println("加密后-->"+encodedStr2);
        String decodedStr2 = Base64CoderCC.decode(encodedStr2);
        System.out.println("解密后-->"+decodedStr2);
        System.out.println(data2.equals(decodedStr2));
    }

    @Test
    public void base64CoderUrl() throws UnsupportedEncodingException {
        testdemo01 d01 = new testdemo01();
        d01.setAdmin(true);
        d01.setSub("1234567890");
        d01.setName("张三");

        String json_str = new Gson().toJson(d01);
        System.out.println(json_str);
        String aa = Base64CoderCC.encodeUrl(json_str);
        System.out.println(aa);
        String bb = Base64CoderCC.decodeUrl(aa);
        System.out.println(Base64CoderCC.decodeUrl(aa));

        System.out.println(Base64CoderCC.hmacSha256("secret_001",bb));
    }

    class testdemo01{
        private String sub;
        private String name;
        private Boolean admin;

        public String getSub() {
            return sub;
        }

        public void setSub(String sub) {
            this.sub = sub;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getAdmin() {
            return admin;
        }

        public void setAdmin(Boolean admin) {
            this.admin = admin;
        }
    }

}