package com.wims.iot.converter;

import com.wims.iot.model.entity.Cata;
import com.wims.iot.model.form.CataForm;
import com.wims.iot.model.vo.CataVO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-16T14:14:44+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 1.8.0_102 (Oracle Corporation)"
)
@Component
public class CataConverterImpl implements CataConverter {

    @Override
    public CataForm entity2Form(Cata entity) {
        if ( entity == null ) {
            return null;
        }

        CataForm cataForm = new CataForm();

        cataForm.setId( entity.getId() );
        cataForm.setName( entity.getName() );
        cataForm.setParentId( entity.getParentId() );
        cataForm.setStatus( entity.getStatus() );
        cataForm.setSort( entity.getSort() );

        return cataForm;
    }

    @Override
    public CataVO entity2Vo(Cata entity) {
        if ( entity == null ) {
            return null;
        }

        CataVO cataVO = new CataVO();

        cataVO.setId( entity.getId() );
        cataVO.setParentId( entity.getParentId() );
        cataVO.setName( entity.getName() );
        cataVO.setSort( entity.getSort() );
        cataVO.setStatus( entity.getStatus() );
        if ( entity.getCreateTime() != null ) {
            cataVO.setCreateTime( LocalDateTime.ofInstant( entity.getCreateTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( entity.getUpdateTime() != null ) {
            cataVO.setUpdateTime( LocalDateTime.ofInstant( entity.getUpdateTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }

        return cataVO;
    }

    @Override
    public Cata form2Entity(CataForm deptForm) {
        if ( deptForm == null ) {
            return null;
        }

        Cata cata = new Cata();

        cata.setId( deptForm.getId() );
        cata.setName( deptForm.getName() );
        cata.setParentId( deptForm.getParentId() );
        cata.setSort( deptForm.getSort() );
        cata.setStatus( deptForm.getStatus() );

        return cata;
    }
}
