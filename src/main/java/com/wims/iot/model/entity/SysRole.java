package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wims.iot.common.base.BaseEntity;
import lombok.Data;

import java.util.Set;

/**
 * 角色表
 */
@TableName(value ="sys_role")
@Data
public class SysRole extends BaseEntity {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 角色状态(1-正常；0-停用)
     */
    private Integer status;

    /**
     * 逻辑删除标识(0-未删除；1-已删除)
     */
    private Integer deleted;

    /**
     * 数据权限
     */
    private Integer dataScope;


    /**
     * 角色描述
     */
    private String description;

    @TableField(exist = false)
    private Set<SysUser> users;

    @TableField(exist = false)
    private Set<SysMenu> menus;

    @TableField(exist = false)
    private Set<SysDept> depts;
}
