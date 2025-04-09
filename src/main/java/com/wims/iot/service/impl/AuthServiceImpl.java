package com.wims.iot.service.impl;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.wims.iot.common.constant.SecurityConstants;
import com.wims.iot.core.security.jwt.JwtTokenProvider;
import com.wims.iot.model.dto.CaptchaResult;
import com.wims.iot.model.dto.LoginResult;
import com.wims.iot.service.AuthService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.awt.Font.SANS_SERIF;

/**
 * 认证服务实现类
 *
 * @author haoxr
 * @since 2.4.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final StringRedisTemplate redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @Override
    public LoginResult login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username.toLowerCase().trim(), password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String accessToken = jwtTokenProvider.createToken(authentication);
        return LoginResult.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    /**
     * 注销
     */
    @Override
    public void logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = jwtTokenProvider.resolveToken(request);
        if (StrUtil.isNotBlank(token) && !"undefined".equals(token)) {
            try {
                Claims claims = jwtTokenProvider.getTokenClaims(token);
                String jti = claims.get("jti", String.class);
                Date expiration = claims.getExpiration();
                if (expiration != null) {
                    long ttl = expiration.getTime() - System.currentTimeMillis();
                    redisTemplate.opsForValue().set(SecurityConstants.BLACK_TOKEN_CACHE_PREFIX + jti, "1", ttl, TimeUnit.MILLISECONDS);
                } else {
                    redisTemplate.opsForValue().set(SecurityConstants.BLACK_TOKEN_CACHE_PREFIX + jti, "1");
                }
            }catch (Exception e) {
                log.error("get token claims failed :",e);
            }
        }
        SecurityContextHolder.clearContext();
    }

    /**
     * 获取验证码
     *
     * @return 验证码
     */
    @Override
    public CaptchaResult getCaptcha() {

        MathGenerator mathGenerator=new MathGenerator(1);
        CircleCaptcha circleCaptcha =new CircleCaptcha(120,25,4,3);
        circleCaptcha.setGenerator(mathGenerator);
        circleCaptcha.setFont(new Font(SANS_SERIF, Font.BOLD, 18));
        String captchaCode = circleCaptcha.getCode(); // 验证码
        String captchaBase64 = circleCaptcha.getImageBase64Data(); // 验证码图片Base64

        // 验证码文本缓存至Redis，用于登录校验
        String captchaKey = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(SecurityConstants.CAPTCHA_CODE_CACHE_PREFIX + captchaKey, captchaCode,
                120, TimeUnit.SECONDS);

        return CaptchaResult.builder()
                .captchaKey(captchaKey)
                .captchaBase64(captchaBase64)
                .build();
    }

}
