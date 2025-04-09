package com.wims.iot.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志切面
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger _logger = LoggerFactory.getLogger(LogAspect.class);


    @Pointcut("execution(public * com.wims.iot.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        _logger.info("URL : " + request.getRequestURL().toString());

        _logger.info("HTTP_METHOD : " + request.getMethod());

        _logger.info("IP : " + request.getRemoteAddr());

        _logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        _logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
//        _logger.info("方法的返回值 : " + ret);
        _logger.info("METHOD_RETURN_VALUE : " + ret);
    }

    //后置异常通知
    @AfterThrowing("webLog()")
    public void afterThrows(JoinPoint jp) {
//        _logger.info("方法异常时执行 : " + jp.toString());
        _logger.info("METHOD_EXCEPTION : " + jp.toString());
    }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("webLog()")
    public void after(JoinPoint jp) {
//        _logger.info("方法最后执行 : " + jp.toString());
        _logger.info("METHOD_FINAL_EXECUTION : " + jp.toString());
    }

    //环绕通知,环绕增强，相当于MethodInterceptor
//    @Around("webLog()")
//    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//        _logger.info("方法环绕start.....");
//        try {
//            Object o = pjp.proceed();
//
//            _logger.info("方法环绕proceed，结果是 :" + o);
//            return o;
//        } catch (Throwable e) {
//            throw e;
//        }
//    }
}
