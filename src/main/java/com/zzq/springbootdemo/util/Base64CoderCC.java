package com.zzq.springbootdemo.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * Description:基于Commons Codec的Base64加密
 * User: TYLER
 * Date: 2019-03-05
 */
public class Base64CoderCC {
    private static final String ENCODING = "UTF-8";

    private static Logger logger = LoggerFactory.getLogger(Base64CoderCC.class);

    /**
     * 一般Base64加密
     */
    public static String encode(String data) throws UnsupportedEncodingException {
        byte[] encodedByte = Base64.encodeBase64(data.getBytes(ENCODING));
        return new String(encodedByte, ENCODING);
    }

    /**
     * 安全Base64加密
     */
    public static String encodeSafe(String data) throws UnsupportedEncodingException{
        /*
         * 注意：这里采用的encodeBase64(byte[] bytes, boolean arg1)
         * arg1为true时，加密后的字符串每行为76个字符，不论每行够不够76个字符，都要在行尾添加“\r\n”
         */
        byte[] encodedByte = Base64.encodeBase64(data.getBytes(ENCODING),true);
        return new String(encodedByte, ENCODING);
    }

    /**
     * Base64解密
     */
    public static String decode(String data) throws UnsupportedEncodingException{
        byte[] decodedByte = Base64.decodeBase64(data.getBytes(ENCODING));
        return new String(decodedByte, ENCODING);
    }

    /**
     * URLBase64加密
     */
    public static String encodeUrl(String data) throws UnsupportedEncodingException{
        byte[] encodedByte = Base64.encodeBase64URLSafe(data.getBytes(ENCODING));
        return new String(encodedByte, ENCODING);
    }
    /**
     * URLBase64解密
     */
    public static String decodeUrl(String data) throws UnsupportedEncodingException{
        byte[] decodedByte = Base64.decodeBase64(data.getBytes(ENCODING));
        return new String(decodedByte, ENCODING);
    }
    /**
     * HMACSHA256算法
     */
    public static String hmacSha256(String secret,String valueToDigest){
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            return Base64.encodeBase64String(sha256_HMAC.doFinal(valueToDigest.getBytes()));
        } catch (Exception e) {
            logger.error("HMACSHA256 Error: "+e.getMessage());
            return null;
        }
    }

}
