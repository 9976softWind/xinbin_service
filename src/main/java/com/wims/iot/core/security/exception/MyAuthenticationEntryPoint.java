package com.wims.iot.core.security.exception;

import com.wims.iot.common.result.ResultCode;
import com.wims.iot.common.util.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理
 *
 * @author haoxr
 * @since 2.0.0
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        int status = response.getStatus();
        if (status == HttpServletResponse.SC_NOT_FOUND) {
            // 资源不存在
            ResponseUtils.writeErrMsg(response, ResultCode.RESOURCE_NOT_FOUND);
        } else {
            // 未认证或者token过期
            ResponseUtils.writeErrMsg(response, ResultCode.TOKEN_INVALID);
        }
    }
}
