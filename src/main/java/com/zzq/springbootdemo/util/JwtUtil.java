package com.zzq.springbootdemo.util;

import com.zzq.springbootdemo.security.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:jwt创建和解析工具类
 * User: TYLER
 * Date: 2019-03-05
 */
public class JwtUtil {
    final static String base64EncodedSecretKey = "SecretKey_zzq";//你的私钥
    final static long TOKEN_EXP = 1000 * 60 * 60;//过期时间,测试使用60分钟
    final static long TOKEN_NBF = 1000 * 60 * 2;//Not Before,测试使用2分钟

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    /*
    --以下是jwt的claim固定可使用字段，更多的字段可以参考io.jsonwebtoken.Claims类

    "iss" => "http://example.org",   #非必须。issuer 请求实体，可以是发起请求的用户的信息，也可是jwt的签发者。
    "iat" => 1356999524,                #非必须。issued at。 token创建时间，unix时间戳格式
    "exp" => "1548333419",            #非必须。expire 指定token的生命周期。unix时间戳格式
    "aud" => "http://example.com",   #非必须。接收该JWT的一方。
    "sub" => "jrocket@example.com",  #非必须。该JWT所面向的用户
    "nbf" => 1357000000,   # 非必须。not before。如果当前时间在nbf里的时间之前，则Token不被接受；一般都会留一些余地，比如几分钟。
    "jti" => '222we',     # 非必须。JWT ID。针对当前token的唯一标识
    * */

    /*JWT的生成*/
    public static String getToken(String userName) {

        return Jwts.builder()
                .setSubject(userName)
                .claim("roles", "user") //设置自定义payload（负载）属性
                .claim("自定义key","自定义value")//设置自定义payload（负载）属性
                .setIssuedAt(new Date()) //创建时间
                .setNotBefore(new Date(System.currentTimeMillis()+TOKEN_NBF))//Not Before时间
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP)) /*过期时间*/
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey) /*采用什么算法是可以自己选择的，不一定非要采用HS256*/
                .compact();
    }

    /*JWT的生成，常用*/
    public static String getToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP)) /*设置过期时间*/
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey) //采用什么算法是可以自己选择的，不一定非要采用HS256
                .compact();
    }
    /*JWT的生成,常用*/
    public static String getToken(Claims claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP)) /*设置过期时间*/
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey) //采用什么算法是可以自己选择的，不一定非要采用HS256
                .compact();
    }
    /*JWT的解析*/
    public static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(base64EncodedSecretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }
    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = getToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证令牌
     *
     * @param token       令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 生成令牌
     *
     * @param userDetails 用户
     * @return 令牌
     */
    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", userDetails.getUsername());
        claims.put("created", new Date());
        return getToken(claims);
    }
}