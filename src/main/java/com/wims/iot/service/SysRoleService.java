package com.wims.iot.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.common.model.Option;
import com.wims.iot.model.dto.AuthorityDto;
import com.wims.iot.model.entity.SysRole;
import com.wims.iot.model.form.RoleForm;
import com.wims.iot.model.query.RolePageQuery;
import com.wims.iot.model.vo.RolePageVO;

import java.util.List;
import java.util.Set;

/**
 * 角色业务接口层
 *
 * @author haoxr
 * @since 2022/6/3
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 角色分页列表
     *
     * @param queryParams
     * @return
     */
    Page<RolePageVO> getRolePage(RolePageQuery queryParams);


    /**
     * 角色下拉列表
     *
     * @return
     */
    List<Option> listRoleOptions();

    /**
     *
     * @param roleForm
     * @return
     */
    boolean saveRole(RoleForm roleForm);

    /**
     * 获取角色表单数据
     *
     * @param roleId 角色ID
     * @return  {@link RoleForm} – 角色表单数据
     */
    RoleForm getRoleForm(Long roleId);

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 角色状态(1:启用；0:禁用)
     * @return {@link Boolean}
     */
    boolean updateRoleStatus(Long roleId, Integer status);

    /**
     * 批量删除角色
     *
     * @param ids 角色ID，多个使用英文逗号(,)分割
     * @return
     */
    boolean deleteRoles(String ids);


    /**
     * 获取角色的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合(包括按钮权限ID)
     */
    List<Long> getRoleMenuIds(Long roleId);


    /**
     * 修改角色的资源权限
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    boolean updateRoleMenus(Long roleId, List<Long> menuIds);

    /**
     * 获取最大范围的数据权限
     *
     * @param roles
     * @return
     */
    Integer getMaximumDataScope(Set<String> roles);


    /**
     * 获取用户权限信息
     * @param userId 用户信息
     * @return 权限信息
     */
    List<AuthorityDto> mapToGrantedAuthorities(Integer userId);

}
