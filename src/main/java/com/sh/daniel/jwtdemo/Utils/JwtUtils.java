package com.sh.daniel.jwtdemo.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    /** 密钥   */
    private static final String SECRET_KEY = "#%g2639jh2";

    /** token 过期时间 */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 10;

    public static String createJWT(){
        //加密算法
        SignatureAlgorithm ignatureAlgorithm = SignatureAlgorithm.HS256;

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        JwtBuilder builder = Jwts.builder()
                .setId("1762893")    //jwt的id
                .setAudience("www.baidu.com") //接受者
                .setIssuer("Daniel")  //签发者信息
                .setClaims(userInfo())   //自定义信息
                .setSubject("JWT主题")  //主题
                .setIssuedAt(new Date()) //签发时间
                .setExpiration(expiresDate) //过期时间
                .signWith(ignatureAlgorithm,SECRET_KEY);
        return  builder.compact();
    }
    //封装用户信息
    private static Map<String,Object> userInfo(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",289341);
        map.put("name","Daniel");
        return map;
    }
    //解密jwt
    public static Claims parseJWTInfo(String jToken){

        return Jwts.parser()
                .setSigningKey(SECRET_KEY).parseClaimsJws(jToken).getBody();

    }

    public static void main(String[] args) {
        String jwt = new JwtUtils().createJWT();
        System.out.println(jwt);

        Claims claims = parseJWTInfo(jwt);

        String subject = claims.getSubject();
        System.out.println(subject);
    }

}
