package com.wims.iot.plugin.dupsubmit.aspect;

import cn.hutool.core.util.StrUtil;
import com.wims.iot.plugin.dupsubmit.annotation.PreventDuplicateSubmit;
import com.wims.iot.core.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理重复提交的切面
 *
 * @author haoxr
 * @since 2.3.0
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DuplicateSubmitAspect {


    private final JwtTokenProvider jwtTokenProvider;
    private static final String RESUBMIT_LOCK_PREFIX = "LOCK:RESUBMIT:";

    /**
     * 防重复提交切点
     */
    @Pointcut("@annotation(preventDuplicateSubmit)")
    public void preventDuplicateSubmitPointCut(PreventDuplicateSubmit preventDuplicateSubmit) {
        log.info("定义防重复提交切点");
    }

    @Around("preventDuplicateSubmitPointCut(preventDuplicateSubmit)")
    public Object doAround(ProceedingJoinPoint pjp, PreventDuplicateSubmit preventDuplicateSubmit) throws Throwable {

        String resubmitLockKey = generateResubmitLockKey();
//        if (resubmitLockKey != null) {
//            int expire = preventDuplicateSubmit.expire(); // 防重提交锁过期时间
//            RLock lock = redissonClient.getLock(resubmitLockKey);
//            boolean lockResult = lock.tryLock(0, expire, TimeUnit.SECONDS); // 获取锁失败，直接返回 false
//            if (!lockResult) {
//                throw new BusinessException(ResultCode.REPEAT_SUBMIT_ERROR); // 抛出重复提交提示信息
//            }
//        }
        return pjp.proceed();
    }


    /**
     * 获取重复提交锁的 key
     */
    private String generateResubmitLockKey() {
        String resubmitLockKey = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String token = jwtTokenProvider.resolveToken(request);
        if (StrUtil.isNotBlank(token) && !"undefined".equals(token)) {
            String jti = (String) jwtTokenProvider.getTokenClaims(token).get("jti");
            resubmitLockKey = RESUBMIT_LOCK_PREFIX + jti + ":" + request.getMethod() + "-" + request.getRequestURI();
        }
        return resubmitLockKey;
    }

}
