package com.wims.iot.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.entity.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuService extends IService<SysRoleMenu> {


    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId
     * @return
     */
    List<Long> listMenuIdsByRoleId(Long roleId);
}
