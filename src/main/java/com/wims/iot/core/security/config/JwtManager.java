package com.wims.iot.core.security.config;

import cn.hutool.core.util.IdUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * JWT工具类
 *
 * @author RudeCrab
 */
@Slf4j
@Component
public class JwtManager {
    @Value("${security.jwt.secretKey}")
    private String secretKey;
    @Value("${security.jwt.tokenHeader}")
    private String tokenHeader;
    /**
     * 过期时间目前设置成1天，这个配置随业务需求而定
     */
    @Value("${security.jwt.expiration}")
    private String expiration;
    @Resource
    private RedisTemplate redisTemplate;
//    private Duration expiration = Duration.ofDays(1);

    /**
     * 生成JWT
     * @param username 用户名
     * @return JWT
     */
    public String generate(String username) {
        // 过期时间
        Date expiryDate = new Date(System.currentTimeMillis() + Long.parseLong(expiration)*1000);
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("jti", IdUtil.fastSimpleUUID());
        claims.put("userAccount", claims.getSubject());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * 解析JWT
     * @param token JWT字符串
     * @return 解析成功返回Claims对象，解析失败返回null
     */
    public Claims parse(String token) {
        // 如果是空字符串直接返回null
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        Claims claims = null;
        // 解析失败了会抛出异常，所以我们要捕捉一下。token过期、token非法都会导致解析失败
        try {

            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (JwtException e) {
            log.error("token解析失败:{}", e.toString());
        }
        return claims;
    }
    //salt，一段加密的字符，这一串东西不能给别人知道不然就不安全了，只有设计者知道
    private static String tokenSignKey = "123456";

    public String generateRsa(String username) {
        // 过期时间
        Date expiryDate = new Date(System.currentTimeMillis() + Long.parseLong(expiration)*1000);
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("jti", IdUtil.fastSimpleUUID());
        claims.put("userAccount", claims.getSubject());
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compact();
    }


}
