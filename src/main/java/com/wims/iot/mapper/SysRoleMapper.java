package com.wims.iot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wims.iot.model.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {


    /**
     * 获取最大范围的数据权限
     *
     * @param roles
     * @return
     */
    Integer getMaximumDataScope(@Param("roles") Set<String> roles);


    List<SysRole> findByUserId(@Param("userId") Integer userId);
}
