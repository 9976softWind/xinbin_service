package com.wims.iot.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 保存用户角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
     boolean saveUserRoles(Long userId, List<Long> roleIds);
}
