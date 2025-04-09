package com.wims.iot.converter;

import com.wims.iot.model.entity.SysMenu;
import com.wims.iot.model.form.MenuForm;
import com.wims.iot.model.vo.MenuVO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-09T11:42:44+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 1.8.0_102 (Oracle Corporation)"
)
@Component
public class MenuConverterImpl implements MenuConverter {

    @Override
    public MenuVO entity2Vo(SysMenu entity) {
        if ( entity == null ) {
            return null;
        }

        MenuVO menuVO = new MenuVO();

        menuVO.setId( entity.getId() );
        menuVO.setParentId( entity.getParentId() );
        menuVO.setName( entity.getName() );
        menuVO.setType( entity.getType() );
        menuVO.setPath( entity.getPath() );
        menuVO.setComponent( entity.getComponent() );
        menuVO.setSort( entity.getSort() );
        menuVO.setVisible( entity.getVisible() );
        menuVO.setIcon( entity.getIcon() );
        menuVO.setRedirect( entity.getRedirect() );
        menuVO.setPerm( entity.getPerm() );

        return menuVO;
    }

    @Override
    public MenuForm entity2Form(SysMenu entity) {
        if ( entity == null ) {
            return null;
        }

        MenuForm menuForm = new MenuForm();

        menuForm.setId( entity.getId() );
        menuForm.setParentId( entity.getParentId() );
        menuForm.setName( entity.getName() );
        menuForm.setType( entity.getType() );
        menuForm.setPath( entity.getPath() );
        menuForm.setComponent( entity.getComponent() );
        menuForm.setPerm( entity.getPerm() );
        menuForm.setVisible( entity.getVisible() );
        menuForm.setSort( entity.getSort() );
        menuForm.setIcon( entity.getIcon() );
        menuForm.setRedirect( entity.getRedirect() );
        menuForm.setKeepAlive( entity.getKeepAlive() );
        menuForm.setAlwaysShow( entity.getAlwaysShow() );

        return menuForm;
    }

    @Override
    public SysMenu form2Entity(MenuForm menuForm) {
        if ( menuForm == null ) {
            return null;
        }

        SysMenu sysMenu = new SysMenu();

        sysMenu.setId( menuForm.getId() );
        sysMenu.setParentId( menuForm.getParentId() );
        sysMenu.setName( menuForm.getName() );
        sysMenu.setType( menuForm.getType() );
        sysMenu.setPath( menuForm.getPath() );
        sysMenu.setComponent( menuForm.getComponent() );
        sysMenu.setPerm( menuForm.getPerm() );
        sysMenu.setVisible( menuForm.getVisible() );
        sysMenu.setSort( menuForm.getSort() );
        sysMenu.setIcon( menuForm.getIcon() );
        sysMenu.setRedirect( menuForm.getRedirect() );
        sysMenu.setKeepAlive( menuForm.getKeepAlive() );
        sysMenu.setAlwaysShow( menuForm.getAlwaysShow() );

        return sysMenu;
    }
}
