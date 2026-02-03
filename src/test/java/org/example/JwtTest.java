package org.example;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    //统一的密钥
    private static final String SECRET_KEY = "aXRoZWltYQ==";

    //生成JWT令牌  Jwts.builder()
    @Test
    public void testGenerateJWT(){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "admin");

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 加密算法，密钥
                .addClaims(dataMap)//自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))//设置过期时间
                .compact();//生成令牌

        System.out.println(jwt);
    }

    //解析JWT令牌
    @Test
    public void testParseJWT(){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "admin");
        // 先生成 JWT（与生成时一致）
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(dataMap)
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .compact();


        // 解析 JWT
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)//密钥
                .parseClaimsJws(token)//解析令牌
                .getBody();//获取自定义信息

        System.out.println(claims);
    }
}