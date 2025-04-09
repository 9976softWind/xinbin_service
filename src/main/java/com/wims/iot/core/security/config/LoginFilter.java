package com.wims.iot.core.security.config;

import com.wims.iot.common.constant.SecurityConstants;
import com.wims.iot.core.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证过滤器
 *
 * @author RudeCrab
 */
@Slf4j
@Component
public class LoginFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtManager;
    private static final AntPathRequestMatcher LOGIN_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(SecurityConstants.LOGOUT_PATH, "DELETE");
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("---LoginFilter---");
        if (LOGIN_PATH_REQUEST_MATCHER.matches(request)) {
            chain.doFilter(request, response);
            return;
        }
        // 从请求头中获取token字符串并解析
        String token = jwtManager.resolveToken(request);
        if (StringUtils.isNotBlank(token) && !"undefined".equals(token)) {
            try {
                Authentication authentication = jwtManager.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e) {
                log.error("parse token error {}",e);
            }
        }

        chain.doFilter(request, response);
    }
}
