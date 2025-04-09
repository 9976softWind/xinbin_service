package com.wims.iot.core.security.config;

import com.wims.iot.common.result.Result;
import com.wims.iot.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证错误处理器
 *
 * @author RudeCrab
 */
@Slf4j
public class MyEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.error("user authentication failed :"+e.getMessage());
//        response.sendRedirect("/login.html");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        Result<Object> result = Result.failed(ResultCode.USER_ERROR, "没有登录");
        out.write(result.toString());
        out.flush();
        out.close();

    }
}
