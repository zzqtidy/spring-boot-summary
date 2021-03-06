package com.zzq.springbootdemo.util;

import com.google.gson.Gson;
import com.zzq.springbootdemo.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: TYLER
 * Date: 2019-03-05
 * Time: 18:30
 */
@Slf4j
public class JwtUtilTest{
    @Test
    public void test01(){
        String encodeToken = JwtUtil.getToken("admin");
        log.info(encodeToken);

        Claims claims = JwtUtil.getClaimsFromToken(encodeToken);
        log.info(new Gson().toJson(claims));


        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 10); /*10h之后*/
        claims
                .setId("id_2019888")
                .setExpiration(cal.getTime())
                .setIssuedAt(new Date())
                .setSubject("admin");

        String encodeToken2 = JwtUtil.getToken(claims);
        log.info(encodeToken2);

        Claims claims2= JwtUtil.getClaimsFromToken(encodeToken2);
        log.info(new Gson().toJson(claims2));

        BCryptPasswordEncoder bCryptPasswordEncoder = new  BCryptPasswordEncoder();

        log.info(bCryptPasswordEncoder.encode("admin"));
        log.info(bCryptPasswordEncoder.encode("libai"));
        log.info(bCryptPasswordEncoder.encode("hanyu"));
        log.info(bCryptPasswordEncoder.encode("liuzongyuan"));
        log.info(bCryptPasswordEncoder.encode("zenggong"));
    }
}