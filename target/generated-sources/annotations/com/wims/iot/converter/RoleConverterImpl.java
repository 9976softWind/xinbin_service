package com.wims.iot.converter;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wims.iot.common.model.Option;
import com.wims.iot.model.entity.SysRole;
import com.wims.iot.model.form.RoleForm;
import com.wims.iot.model.vo.RolePageVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-09T11:42:44+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 1.8.0_102 (Oracle Corporation)"
)
@Component
public class RoleConverterImpl implements RoleConverter {

    @Override
    public Page<RolePageVO> entity2Page(Page<SysRole> page) {
        if ( page == null ) {
            return null;
        }

        Page<RolePageVO> page1 = new Page<RolePageVO>();

        page1.setPages( page.getPages() );
        page1.setRecords( sysRoleListToRolePageVOList( page.getRecords() ) );
        page1.setTotal( page.getTotal() );
        page1.setSize( page.getSize() );
        page1.setCurrent( page.getCurrent() );
        page1.setSearchCount( page.isSearchCount() );
        page1.setOptimizeCountSql( page.isOptimizeCountSql() );
        List<OrderItem> list1 = page.getOrders();
        if ( list1 != null ) {
            page1.setOrders( new ArrayList<OrderItem>( list1 ) );
        }
        page1.setCountId( page.getCountId() );
        page1.setMaxLimit( page.getMaxLimit() );

        return page1;
    }

    @Override
    public Option entity2Option(SysRole role) {
        if ( role == null ) {
            return null;
        }

        Option option = new Option();

        option.setValue( role.getId() );
        option.setLabel( role.getName() );

        return option;
    }

    @Override
    public List<Option> entities2Options(List<SysRole> roles) {
        if ( roles == null ) {
            return null;
        }

        List<Option> list = new ArrayList<Option>( roles.size() );
        for ( SysRole sysRole : roles ) {
            list.add( entity2Option( sysRole ) );
        }

        return list;
    }

    @Override
    public SysRole form2Entity(RoleForm roleForm) {
        if ( roleForm == null ) {
            return null;
        }

        SysRole sysRole = new SysRole();

        sysRole.setId( roleForm.getId() );
        sysRole.setName( roleForm.getName() );
        sysRole.setCode( roleForm.getCode() );
        sysRole.setSort( roleForm.getSort() );
        sysRole.setStatus( roleForm.getStatus() );
        sysRole.setDataScope( roleForm.getDataScope() );

        return sysRole;
    }

    @Override
    public RoleForm entity2Form(SysRole entity) {
        if ( entity == null ) {
            return null;
        }

        RoleForm roleForm = new RoleForm();

        roleForm.setId( entity.getId() );
        roleForm.setName( entity.getName() );
        roleForm.setCode( entity.getCode() );
        roleForm.setSort( entity.getSort() );
        roleForm.setStatus( entity.getStatus() );
        roleForm.setDataScope( entity.getDataScope() );

        return roleForm;
    }

    protected RolePageVO sysRoleToRolePageVO(SysRole sysRole) {
        if ( sysRole == null ) {
            return null;
        }

        RolePageVO rolePageVO = new RolePageVO();

        rolePageVO.setId( sysRole.getId() );
        rolePageVO.setName( sysRole.getName() );
        rolePageVO.setCode( sysRole.getCode() );
        rolePageVO.setStatus( sysRole.getStatus() );
        rolePageVO.setSort( sysRole.getSort() );
        rolePageVO.setCreateTime( sysRole.getCreateTime() );
        rolePageVO.setUpdateTime( sysRole.getUpdateTime() );

        return rolePageVO;
    }

    protected List<RolePageVO> sysRoleListToRolePageVOList(List<SysRole> list) {
        if ( list == null ) {
            return null;
        }

        List<RolePageVO> list1 = new ArrayList<RolePageVO>( list.size() );
        for ( SysRole sysRole : list ) {
            list1.add( sysRoleToRolePageVO( sysRole ) );
        }

        return list1;
    }
}
