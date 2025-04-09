package com.wims.iot.core.security.config;

import com.wims.iot.model.entity.SysMenu;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author RudeCrab
 */
@Slf4j
@Component
public class MySecurityMetadataSource implements SecurityMetadataSource {
    /**
     * 当前系统所有url资源
     */

    @Getter
    private static  Map<Integer, SysMenu> menuMap = new ConcurrentHashMap<>();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        // 该对象是Spring Security帮我们封装好的，可以通过该对象获取request等信息
//        FilterInvocation filterInvocation = (FilterInvocation) object;
//        HttpServletRequest request = filterInvocation.getRequest();
//        // 遍历所有权限资源，以和当前请求所需的权限进行匹配
//        for (Map.Entry<Long, SysPermission> entry : permissionMap.entrySet()) {
//            SysPermission sysPermission = entry.getValue();
//            // 因为/API/user/test/{id}这种路径参数不能直接equals来判断请求路径是否匹配，所以需要用Ant类来匹配
//            AntPathRequestMatcher ant = new AntPathRequestMatcher(sysPermission.getPermissionHref());
//            // 如果请求方法和请求路径都匹配上了，则代表找到了这个请求所需的权限资源
//            if (ant.matches(request)) {
//                // 将我们权限资源id返回
//                return Collections.singletonList(new SecurityConfig(sysPermission.getPermissionCode()));
//            }
//        }

        // 走到这里就代表该请求无需授权即可访问，返回空
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }


    public static void main(String[] args) {
        AntPathMatcher ant = new AntPathMatcher();
        boolean match = ant.match("/tbFile/upload", "/tbFile/upload");
        System.out.println(match);
    }

}
