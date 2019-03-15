package com.zzq.springbootdemo.util;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class JwtUtilTest {
    private static Logger logger = LoggerFactory.getLogger(JwtUtilTest.class);
    @Test
    public void test01(){
        String encodeToken = JwtUtil.getToken("admin");
        logger.info(encodeToken);

        Claims claims = JwtUtil.getClaimsFromToken(encodeToken);
        logger.info(new Gson().toJson(claims));


        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 10); /*10h之后*/
        claims
                .setId("id_2019888")
                .setExpiration(cal.getTime())
                .setIssuedAt(new Date())
                .setSubject("admin");

        String encodeToken2 = JwtUtil.getToken(claims);
        logger.info(encodeToken2);

        Claims claims2= JwtUtil.getClaimsFromToken(encodeToken2);
        logger.info(new Gson().toJson(claims2));

        BCryptPasswordEncoder bCryptPasswordEncoder = new  BCryptPasswordEncoder();

        logger.info(bCryptPasswordEncoder.encode("admin"));
        logger.info(bCryptPasswordEncoder.encode("libai"));
        logger.info(bCryptPasswordEncoder.encode("hanyu"));
        logger.info(bCryptPasswordEncoder.encode("liuzongyuan"));
        logger.info(bCryptPasswordEncoder.encode("zenggong"));

    }
}