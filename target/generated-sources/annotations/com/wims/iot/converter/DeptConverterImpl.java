package com.wims.iot.converter;

import com.wims.iot.model.entity.SysDept;
import com.wims.iot.model.form.DeptForm;
import com.wims.iot.model.vo.DeptVO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-09T11:42:44+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 1.8.0_102 (Oracle Corporation)"
)
@Component
public class DeptConverterImpl implements DeptConverter {

    @Override
    public DeptForm entity2Form(SysDept entity) {
        if ( entity == null ) {
            return null;
        }

        DeptForm deptForm = new DeptForm();

        deptForm.setId( entity.getId() );
        deptForm.setName( entity.getName() );
        deptForm.setParentId( entity.getParentId() );
        deptForm.setStatus( entity.getStatus() );
        deptForm.setSort( entity.getSort() );

        return deptForm;
    }

    @Override
    public DeptVO entity2Vo(SysDept entity) {
        if ( entity == null ) {
            return null;
        }

        DeptVO deptVO = new DeptVO();

        deptVO.setId( entity.getId() );
        deptVO.setParentId( entity.getParentId() );
        deptVO.setName( entity.getName() );
        deptVO.setSort( entity.getSort() );
        deptVO.setStatus( entity.getStatus() );
        deptVO.setCreateTime( entity.getCreateTime() );
        deptVO.setUpdateTime( entity.getUpdateTime() );

        return deptVO;
    }

    @Override
    public SysDept form2Entity(DeptForm deptForm) {
        if ( deptForm == null ) {
            return null;
        }

        SysDept sysDept = new SysDept();

        sysDept.setId( deptForm.getId() );
        sysDept.setName( deptForm.getName() );
        sysDept.setParentId( deptForm.getParentId() );
        sysDept.setSort( deptForm.getSort() );
        sysDept.setStatus( deptForm.getStatus() );

        return sysDept;
    }
}
