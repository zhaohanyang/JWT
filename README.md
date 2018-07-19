# JWTDemo

jwt的构成:
  第一部分称它为头部(header)
  第二部分为载荷(payload)
  第三部分为签名(signature)  

摘自jwt.io的JToken实例:
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c

可以看到由三个英文句号点间隔开,这三部分就对应着上面的三个信息

jwt构成解释:

    header:包含声明类型和加密算(通常为HMAC SHA256)
           {
            'typ':'jwt',
            'alg':'HS256'
           }
           再通过base64加密
              
    payload: 载荷存放有效信息的地方,包含三部分:注册的声明,公共的声明,私有的声明
             注册的声明:iss(jwt签发人信息),sub(jwt面向的用户),aud(接收jwt的一方),exp(过期时间),nbf(生效时间),iat(签发时间),jti(jwt唯一身份标识)
             公共的声明:可以添加任意信息,可以添加和自己业务有关的例如用户相关信息,用户请求的时候服务端进行解密信息,但勿存储敏感信息
             私有的声明:不建议存放敏感信息
             实例:
             {
                "sub": "1234567890",
                 "name": "John Doe",
                 "iat": 1516239022
             }
             再由base64进行加密
             
    signature: 由三部分组成:base64加密后的header,payload以及secret密钥

    注意: 密钥一定不能泄漏
    
    JWT的简单使用:
    
        1: 添加maven依赖
        <dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.7.0</version>
		</dependency>
        2:代码
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


    
    
