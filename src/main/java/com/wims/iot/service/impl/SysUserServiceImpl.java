package com.wims.iot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wims.iot.common.constant.SecurityConstants;
import com.wims.iot.common.constant.SystemConstants;
import com.wims.iot.common.util.DateUtils;
import com.wims.iot.common.util.SecurityUtils;
import com.wims.iot.converter.UserConverter;
import com.wims.iot.mapper.SysUserMapper;
import com.wims.iot.model.bo.UserBO;
import com.wims.iot.model.bo.UserFormBO;
import com.wims.iot.model.dto.AuthorityDto;
import com.wims.iot.model.dto.UserAuthInfo;
import com.wims.iot.model.entity.SysUser;
import com.wims.iot.model.form.UserForm;
import com.wims.iot.model.query.UserPageQuery;
import com.wims.iot.model.vo.UserExportVO;
import com.wims.iot.model.vo.UserInfoVO;
import com.wims.iot.model.vo.UserPageVO;
import com.wims.iot.service.SysMenuService;
import com.wims.iot.service.SysRoleService;
import com.wims.iot.service.SysUserRoleService;
import com.wims.iot.service.SysUserService;
import com.wims.iot.util.ApplicationHandler;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户业务实现类
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private  PasswordEncoder passwordEncoder;
    @Resource
    private  SysUserRoleService userRoleService;
    @Resource
    private  UserConverter userConverter;
    @Resource
    private  SysMenuService menuService;
    @Resource
    private  SysRoleService roleService;
    @Resource
    private  RedisTemplate redisTemplate;


    /**
     * 获取用户分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public IPage<UserPageVO> getUserPage(UserPageQuery queryParams) {

        // 参数构建
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<UserBO> page = new Page<>(pageNum, pageSize);

        // 格式化为数据库日期格式，避免日期比较使用格式化函数导致索引失效
        DateUtils.toDatabaseFormat(queryParams, "startTime", "endTime");

        // 查询数据
        Page<UserBO> userPage = this.baseMapper.getUserPage(page, queryParams);

        // 实体转换
        return userConverter.toPageVo(userPage);
    }

    /**
     * 获取用户详情
     *
     * @param userId
     * @return
     */
    @Override
    public UserForm getUserFormData(Long userId) {
        UserFormBO userFormBO = this.baseMapper.getUserDetail(userId);
        // 实体转换po->form
        UserForm userForm = userConverter.bo2Form(userFormBO);
        return userForm;
    }

    /**
     * 新增用户
     *
     * @param userForm 用户表单对象
     * @return
     */
    @Override
    public boolean saveUser(UserForm userForm) {

        String username = userForm.getUsername();

        long count = this.count(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        Assert.isTrue(count == 0, "用户名已存在");

        // 实体转换 form->entity
        SysUser entity = userConverter.form2Entity(userForm);

        // 设置默认加密密码
        String defaultEncryptPwd = passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD);
        entity.setPassword(defaultEncryptPwd);

        // 新增用户
        boolean result = this.save(entity);

        if (result) {
            // 保存用户角色
            userRoleService.saveUserRoles(entity.getId(), userForm.getRoleIds());
        }
        return result;
    }

    /**
     * 更新用户
     *
     * @param userId   用户ID
     * @param userForm 用户表单对象
     * @return
     */
    @Override
    @Transactional
    public boolean updateUser(Long userId, UserForm userForm) {

        String username = userForm.getUsername();

        long count = this.count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)
                .ne(SysUser::getId, userId)
        );
        Assert.isTrue(count == 0, "用户名已存在");

        // form -> entity
        SysUser entity = userConverter.form2Entity(userForm);

        // 修改用户
        boolean result = this.updateById(entity);

        if (result) {
            // 保存用户角色
            userRoleService.saveUserRoles(entity.getId(), userForm.getRoleIds());
        }
        return result;
    }

    /**
     * 删除用户
     *
     * @param idsStr 用户ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteUsers(String idsStr) {
        Assert.isTrue(StrUtil.isNotBlank(idsStr), "删除的用户数据为空");
        // 逻辑删除
        List<Long> ids = Arrays.asList(idsStr.split(",")).stream()
                .map(idStr -> Long.parseLong(idStr)).collect(Collectors.toList());
        boolean result = this.removeByIds(ids);
        return result;

    }

    /**
     * 修改用户密码
     *
     * @param userId   用户ID
     * @param password 用户密码
     * @return true|false
     */
    @Override
    public boolean updatePassword(Long userId, String password) {
        return this.update(new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getId, userId)
                .set(SysUser::getPassword, passwordEncoder.encode(password))
        );
    }

    /**
     * 根据用户名获取认证信息
     *
     * @param username 用户名
     * @return 用户认证信息 {@link UserAuthInfo}
     */
    @Override
    public UserAuthInfo getUserAuthInfo(String username) {
        UserAuthInfo userAuthInfo = this.baseMapper.getUserAuthInfo(username);
        if (userAuthInfo != null) {
            Set<String> roles = userAuthInfo.getRoles();
            if (CollectionUtil.isNotEmpty(roles)) {
                Set<String> perms = menuService.listRolePerms(roles);
                userAuthInfo.setPerms(perms);
            }

            // 获取最大范围的数据权限
            Integer dataScope = roleService.getMaximumDataScope(roles);
            userAuthInfo.setDataScope(dataScope);
        }
        return userAuthInfo;
    }


    /**
     * 获取导出用户列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public List<UserExportVO> listExportUsers(UserPageQuery queryParams) {
        List<UserExportVO> list = this.baseMapper.listExportUsers(queryParams);
        return list;
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    @Override
    public UserInfoVO getCurrentUserInfo() {

        String username = SecurityUtils.getUser().getUsername(); // 登录用户名

        // 获取登录用户基础信息
        SysUser user = this.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)
                .select(
                        SysUser::getId,
                        SysUser::getUsername,
                        SysUser::getNickname,
                        SysUser::getAvatar
                )
        );
        // entity->VO
        UserInfoVO userInfoVO = userConverter.toUserInfoVo(user);

        // 用户角色集合
        Set<String> roles = SecurityUtils.getRoles();
        userInfoVO.setRoles(roles);

        // 用户权限集合
        List<AuthorityDto> authorityDtoList = roleService.mapToGrantedAuthorities(ApplicationHandler.getBean(SysUserMapper.class).findIdByUsername(username));
        Set<String> perms = authorityDtoList.stream().map(AuthorityDto::getAuthority).collect(Collectors.toSet());
        userInfoVO.setPerms(perms);

        // 权限数据多放入Redis
        redisTemplate.opsForValue().set(SecurityConstants.USER_PERMS_CACHE_PREFIX + user.getId(), perms);

        return userInfoVO;
    }


}
