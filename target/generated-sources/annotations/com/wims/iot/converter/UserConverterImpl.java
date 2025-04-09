package com.wims.iot.converter;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wims.iot.model.bo.UserBO;
import com.wims.iot.model.bo.UserFormBO;
import com.wims.iot.model.entity.SysUser;
import com.wims.iot.model.form.UserForm;
import com.wims.iot.model.vo.UserImportVO;
import com.wims.iot.model.vo.UserInfoVO;
import com.wims.iot.model.vo.UserPageVO;
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
public class UserConverterImpl implements UserConverter {

    @Override
    public UserPageVO toPageVo(UserBO bo) {
        if ( bo == null ) {
            return null;
        }

        UserPageVO userPageVO = new UserPageVO();

        userPageVO.setId( bo.getId() );
        userPageVO.setUsername( bo.getUsername() );
        userPageVO.setNickname( bo.getNickname() );
        userPageVO.setMobile( bo.getMobile() );
        userPageVO.setAvatar( bo.getAvatar() );
        userPageVO.setEmail( bo.getEmail() );
        userPageVO.setStatus( bo.getStatus() );
        userPageVO.setDeptName( bo.getDeptName() );
        userPageVO.setRoleNames( bo.getRoleNames() );
        userPageVO.setCreateTime( bo.getCreateTime() );

        userPageVO.setGenderLabel( com.wims.iot.common.base.IBaseEnum.getLabelByValue(bo.getGender(), com.wims.iot.common.enums.GenderEnum.class) );

        return userPageVO;
    }

    @Override
    public Page<UserPageVO> toPageVo(Page<UserBO> bo) {
        if ( bo == null ) {
            return null;
        }

        Page<UserPageVO> page = new Page<UserPageVO>();

        page.setPages( bo.getPages() );
        page.setRecords( userBOListToUserPageVOList( bo.getRecords() ) );
        page.setTotal( bo.getTotal() );
        page.setSize( bo.getSize() );
        page.setCurrent( bo.getCurrent() );
        page.setSearchCount( bo.isSearchCount() );
        page.setOptimizeCountSql( bo.isOptimizeCountSql() );
        List<OrderItem> list1 = bo.getOrders();
        if ( list1 != null ) {
            page.setOrders( new ArrayList<OrderItem>( list1 ) );
        }
        page.setCountId( bo.getCountId() );
        page.setMaxLimit( bo.getMaxLimit() );

        return page;
    }

    @Override
    public UserForm bo2Form(UserFormBO bo) {
        if ( bo == null ) {
            return null;
        }

        UserForm userForm = new UserForm();

        userForm.setId( bo.getId() );
        userForm.setUsername( bo.getUsername() );
        userForm.setNickname( bo.getNickname() );
        userForm.setMobile( bo.getMobile() );
        userForm.setGender( bo.getGender() );
        userForm.setAvatar( bo.getAvatar() );
        userForm.setEmail( bo.getEmail() );
        userForm.setStatus( bo.getStatus() );
        userForm.setDeptId( bo.getDeptId() );
        List<Long> list = bo.getRoleIds();
        if ( list != null ) {
            userForm.setRoleIds( new ArrayList<Long>( list ) );
        }

        return userForm;
    }

    @Override
    public UserForm entity2Form(SysUser entity) {
        if ( entity == null ) {
            return null;
        }

        UserForm userForm = new UserForm();

        userForm.setId( entity.getId() );
        userForm.setUsername( entity.getUsername() );
        userForm.setNickname( entity.getNickname() );
        userForm.setMobile( entity.getMobile() );
        userForm.setGender( entity.getGender() );
        userForm.setAvatar( entity.getAvatar() );
        userForm.setEmail( entity.getEmail() );
        userForm.setStatus( entity.getStatus() );
        userForm.setDeptId( entity.getDeptId() );

        return userForm;
    }

    @Override
    public SysUser form2Entity(UserForm entity) {
        if ( entity == null ) {
            return null;
        }

        SysUser sysUser = new SysUser();

        sysUser.setId( entity.getId() );
        sysUser.setUsername( entity.getUsername() );
        sysUser.setNickname( entity.getNickname() );
        sysUser.setGender( entity.getGender() );
        sysUser.setDeptId( entity.getDeptId() );
        sysUser.setAvatar( entity.getAvatar() );
        sysUser.setMobile( entity.getMobile() );
        sysUser.setStatus( entity.getStatus() );
        sysUser.setEmail( entity.getEmail() );

        return sysUser;
    }

    @Override
    public UserInfoVO toUserInfoVo(SysUser entity) {
        if ( entity == null ) {
            return null;
        }

        UserInfoVO userInfoVO = new UserInfoVO();

        userInfoVO.setUserId( entity.getId() );
        userInfoVO.setUsername( entity.getUsername() );
        userInfoVO.setNickname( entity.getNickname() );
        userInfoVO.setAvatar( entity.getAvatar() );

        return userInfoVO;
    }

    @Override
    public SysUser importVo2Entity(UserImportVO vo) {
        if ( vo == null ) {
            return null;
        }

        SysUser sysUser = new SysUser();

        sysUser.setUsername( vo.getUsername() );
        sysUser.setNickname( vo.getNickname() );
        if ( vo.getGender() != null ) {
            sysUser.setGender( Integer.parseInt( vo.getGender() ) );
        }
        sysUser.setMobile( vo.getMobile() );
        sysUser.setEmail( vo.getEmail() );

        return sysUser;
    }

    protected List<UserPageVO> userBOListToUserPageVOList(List<UserBO> list) {
        if ( list == null ) {
            return null;
        }

        List<UserPageVO> list1 = new ArrayList<UserPageVO>( list.size() );
        for ( UserBO userBO : list ) {
            list1.add( toPageVo( userBO ) );
        }

        return list1;
    }
}
