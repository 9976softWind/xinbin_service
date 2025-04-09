package com.wims.iot.core.security.config;

import com.wims.iot.model.dto.AuthorityDto;
import com.wims.iot.model.dto.UserAuthInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义用户对象
 *
 * @author RudeCrab
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserDetail extends User {

    private Long userId;

    private String username;

    private String nickName;

    private String password;

    private Set<Integer> roleIds;

    private Long deptId;

    private Integer dataScope;

    private Set<String> perms;

    private Set<String> roles;
    public UserDetail(UserAuthInfo sysUser, Collection<? extends GrantedAuthority> authorities, List<AuthorityDto> authorityDtoList) {

        // 必须调用父类的构造方法，初始化用户名、密码、权限
        super(sysUser.getUsername(), sysUser.getPassword(), authorities);
        this.userId = sysUser.getUserId();
        this.username = sysUser.getUsername();
        this.nickName = sysUser.getNickname();
        this.password = sysUser.getPassword();
        this.roleIds = sysUser.getRoleIds();
        this.deptId = sysUser.getDeptId();
        this.dataScope = sysUser.getDataScope();
        this.roles = sysUser.getRoles();
        this.perms = authorityDtoList.stream().map(AuthorityDto::getAuthority).collect(Collectors.toSet());
    }
}
