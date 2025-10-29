package com.example.swinedatebaseproject.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author 123
 * @Date 2022/11/14
 */
public class JwtUtils {

    /**
     * JWT密钥
     */
    private static final SecretKey JWT_SECRET_KEY = Keys.hmacShaKeyFor("MyFirstBigProjectNameChatRoomWithDD".getBytes());

    // 签名算法为HS256
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    // JWT的开始时间，通常设置为当前时间
    private static final Date START = new Date();

    /**
     * 创建JWT令牌
     *
     * @param subject JWT主题
     * @param claims  包含在JWT中的声明
     * @return JWT令牌字符串
     */
    public static String createJwt(String subject, Map<String, String> claims) {

        START.setTime(System.currentTimeMillis());

        JwtBuilder jwtBuilder = Jwts.builder()
                // 设置JWT的身份标识
                .setId(UUID.randomUUID().toString())
                // 设置JWT的签发时间
                .setIssuedAt(START)
                // 设置JWT的主题
                .setSubject(subject)
                // 使用密钥进行签名
                .signWith(JWT_SECRET_KEY, SIGNATURE_ALGORITHM);

        // 单独添加有效数据部分
        if (Objects.nonNull(claims)) {
            claims.forEach(jwtBuilder::claim);
        }
        return jwtBuilder.compact();
    }

    /**
     * 解析JWT令牌并返回其声明部分
     *
     * @param jwt JWT令牌字符串
     * @return 包含JWT声明的Claims对象
     */
    @SuppressWarnings("all")
    public static Claims parseJWT(String jwt) {
        return (Claims) Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parse(jwt)
                .getBody();
    }

    /**
     * 解析JWT令牌并返回其字符串表示形式
     *
     * @param jwt JWT令牌字符串
     * @return JWT令牌的字符串表示形式
     */
    @SuppressWarnings("all")
    public static String getJWT(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parse(jwt)
                .toString();
    }
}
