package com.wims.iot.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wims.iot.common.exception.BusinessException;
import com.wims.iot.common.result.UserCode;
import com.wims.iot.core.security.config.UserDetail;
import com.wims.iot.mapper.SysUserMapper;
import com.wims.iot.model.dto.UserAuthInfo;
import com.wims.iot.model.entity.SysUser;
import com.wims.iot.service.SysMenuService;
import com.wims.iot.service.SysRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Primary
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private SysRoleService roleService;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysMenuService sysMenuService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getUsername,username);
        List<SysUser> sysUsers = sysUserMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(sysUsers)) {
            throw new BusinessException(UserCode.USER_NOT_EXIST);
        }

        UserAuthInfo userAuthInfo = sysUserMapper.getUserAuthInfo(username);
        if (userAuthInfo != null) {
            Set<String> roles = userAuthInfo.getRoles();
            if (CollectionUtil.isNotEmpty(roles)) {
                Set<String> perms = sysMenuService.listRolePerms(roles);
                userAuthInfo.setPerms(perms);
            }

            // 获取最大范围的数据权限
            Integer dataScope = roleService.getMaximumDataScope(roles);
            userAuthInfo.setDataScope(dataScope);
        }

        Set<SimpleGrantedAuthority> authorities = null;
        if (CollectionUtils.isEmpty(userAuthInfo.getRoles())) {
            authorities = new HashSet<>();
        } else {
            authorities = userAuthInfo.getRoles()
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
        // 查询权限id
        return new UserDetail(userAuthInfo,authorities,roleService.mapToGrantedAuthorities(sysUserMapper.findIdByUsername(username)));
    }

}
