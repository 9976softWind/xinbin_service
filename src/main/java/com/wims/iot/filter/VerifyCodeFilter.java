package com.wims.iot.filter;

import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.wims.iot.common.constant.SecurityConstants;
import com.wims.iot.common.result.ResultCode;
import com.wims.iot.common.util.ResponseUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 验证码校验过滤器
 *
 * @author haoxr
 * @since 2022/10/1
 */
public class VerifyCodeFilter extends OncePerRequestFilter{

    private static final AntPathRequestMatcher LOGIN_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(SecurityConstants.LOGIN_PATH, "POST");

    public static final String CAPTCHA_CODE_PARAM_NAME = "captchaCode";
    public static final String CAPTCHA_KEY_PARAM_NAME = "captchaKey";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (LOGIN_PATH_REQUEST_MATCHER.matches(request)) {
            // 请求中的验证码
            String verifyCode = request.getParameter(CAPTCHA_CODE_PARAM_NAME);
            // TODO 兼容没有验证码的版本(线上请移除这个判断)
            if (StrUtil.isBlank(verifyCode)) {
                chain.doFilter(request, response);
                return;
            }
            // 缓存中的验证码
            StringRedisTemplate redisTemplate = SpringUtil.getBean("stringRedisTemplate", StringRedisTemplate.class);
            String verifyCodeKey = request.getParameter(CAPTCHA_KEY_PARAM_NAME);
            String cacheVerifyCode = redisTemplate.opsForValue().get(SecurityConstants.CAPTCHA_CODE_CACHE_PREFIX + verifyCodeKey);
            if (cacheVerifyCode == null) {
                ResponseUtils.writeErrMsg(response, ResultCode.VERIFY_CODE_TIMEOUT);
            } else {
                // 验证码比对
                MathGenerator mathGenerator = new MathGenerator();
                if (mathGenerator.verify(cacheVerifyCode, verifyCode)) {
                    chain.doFilter(request, response);
                } else {
                    ResponseUtils.writeErrMsg(response, ResultCode.VERIFY_CODE_ERROR);
                }
            }
        } else {
            // 非登录接口放行
            chain.doFilter(request, response);
        }
    }
}
