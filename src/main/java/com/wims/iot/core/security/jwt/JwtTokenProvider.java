package com.wims.iot.core.security.jwt;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import com.wims.iot.common.constant.SecurityConstants;
import com.wims.iot.common.exception.BusinessException;
import com.wims.iot.common.result.ResultCode;
import com.wims.iot.core.security.config.UserDetail;
import com.wims.iot.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JWT token 管理器
 *
 * @author haoxr
 * @since 2023/9/13
 */

@Component
@Slf4j
public class JwtTokenProvider {

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${jwt.secret-key:123456}")
    private String secretKey;

    /**
     *过期时间：24h
     */
    @Value("${jwt.expiration:86400}")
    private int expiration;

    private byte[] secretKeyBytes;
    @Resource
    private UserDetailsServiceImpl userService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * 创建Token
     *
     * @param authentication
     * @return
     */
    public String createToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());

        UserDetail userDetails = (UserDetail) authentication.getPrincipal();
        claims.put("userId", userDetails.getUserId());
        claims.put("username", claims.getSubject());
        claims.put("deptId", userDetails.getDeptId());
        claims.put("dataScope", userDetails.getDataScope());
        claims.put("jti", IdUtil.fastSimpleUUID());
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        claims.put("authorities", roles);

        // 权限数据多放入Redis
        Set<String> perms = userDetails.getPerms();
        redisTemplate.opsForValue().set(SecurityConstants.USER_PERMS_CACHE_PREFIX + userDetails.getUserId(), perms);


        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + expiration * 1000L);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(Keys.hmacShaKeyFor(getSecretKeyBytes()), SignatureAlgorithm.HS256).compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = this.getTokenClaims(token);
        UserDetails userdetails = userService.loadUserByUsername(Convert.toStr(claims.get("username")));
        return new UsernamePasswordAuthenticationToken(userdetails, "", userdetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(getSecretKeyBytes()).build().parseClaimsJws(token);
        return true;
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }


    public Claims getTokenClaims(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(this.getSecretKeyBytes()).build().parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e) {
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }

    }


    public byte[] getSecretKeyBytes() {
        if (secretKeyBytes == null) {
            try {
                secretKeyBytes = Decoders.BASE64.decode(secretKey);
            } catch (DecodingException e) {
                secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            }
        }
        return secretKeyBytes;
    }


}
