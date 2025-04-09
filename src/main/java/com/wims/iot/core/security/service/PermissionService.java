package com.wims.iot.core.security.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.wims.iot.common.util.SecurityUtils;
import com.wims.iot.mapper.SysUserMapper;
import com.wims.iot.model.dto.AuthorityDto;
import com.wims.iot.service.SysRoleService;
import com.wims.iot.util.ApplicationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SpringSecurity 权限校验
 *
 * @author haoxr
 * @since 2022/2/22
 */
@Component("ss")
@RequiredArgsConstructor
@Slf4j
public class PermissionService {

    private final RedisTemplate redisTemplate;

    /**
     * 判断当前登录用户是否拥有操作权限
     *
     * @param perm 权限标识(eg: sys:user:add)
     * @return
     */
    public boolean hasPerm(String perm) {

        if (StrUtil.isBlank(perm)) {
            return false;
        }
        // 超级管理员放行
        if (SecurityUtils.isRoot()) {
            return true;
        }

        Long userId = SecurityUtils.getUserId();

        // 用户权限集合
        List<AuthorityDto> authorityDtoList = ApplicationHandler.getBean(SysRoleService.class).mapToGrantedAuthorities(ApplicationHandler.getBean(SysUserMapper.class).findIdByUsername(SecurityUtils.getUser().getUsername()));
        Set<String> perms = authorityDtoList.stream().map(AuthorityDto::getAuthority).collect(Collectors.toSet());

        if (CollectionUtil.isEmpty(perms)) {
            return false;
        }
        boolean hasPermission = perms.stream()
                .anyMatch(item -> PatternMatchUtils.simpleMatch(perm, item)); // *号匹配任意字符

        if (!hasPermission) {
            log.error("用户无访问权限");
        }
        return hasPermission;
    }


}
