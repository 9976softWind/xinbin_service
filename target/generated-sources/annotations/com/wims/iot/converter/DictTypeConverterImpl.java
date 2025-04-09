package com.wims.iot.converter;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wims.iot.model.entity.SysDictType;
import com.wims.iot.model.form.DictTypeForm;
import com.wims.iot.model.vo.DictTypePageVO;
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
public class DictTypeConverterImpl implements DictTypeConverter {

    @Override
    public Page<DictTypePageVO> entity2Page(Page<SysDictType> page) {
        if ( page == null ) {
            return null;
        }

        Page<DictTypePageVO> page1 = new Page<DictTypePageVO>();

        page1.setPages( page.getPages() );
        page1.setRecords( sysDictTypeListToDictTypePageVOList( page.getRecords() ) );
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
    public DictTypeForm entity2Form(SysDictType entity) {
        if ( entity == null ) {
            return null;
        }

        DictTypeForm dictTypeForm = new DictTypeForm();

        dictTypeForm.setId( entity.getId() );
        dictTypeForm.setName( entity.getName() );
        dictTypeForm.setCode( entity.getCode() );
        dictTypeForm.setStatus( entity.getStatus() );
        dictTypeForm.setRemark( entity.getRemark() );

        return dictTypeForm;
    }

    @Override
    public SysDictType form2Entity(DictTypeForm entity) {
        if ( entity == null ) {
            return null;
        }

        SysDictType sysDictType = new SysDictType();

        sysDictType.setId( entity.getId() );
        sysDictType.setName( entity.getName() );
        sysDictType.setCode( entity.getCode() );
        sysDictType.setStatus( entity.getStatus() );
        sysDictType.setRemark( entity.getRemark() );

        return sysDictType;
    }

    protected DictTypePageVO sysDictTypeToDictTypePageVO(SysDictType sysDictType) {
        if ( sysDictType == null ) {
            return null;
        }

        DictTypePageVO dictTypePageVO = new DictTypePageVO();

        dictTypePageVO.setId( sysDictType.getId() );
        dictTypePageVO.setName( sysDictType.getName() );
        dictTypePageVO.setCode( sysDictType.getCode() );
        dictTypePageVO.setStatus( sysDictType.getStatus() );

        return dictTypePageVO;
    }

    protected List<DictTypePageVO> sysDictTypeListToDictTypePageVOList(List<SysDictType> list) {
        if ( list == null ) {
            return null;
        }

        List<DictTypePageVO> list1 = new ArrayList<DictTypePageVO>( list.size() );
        for ( SysDictType sysDictType : list ) {
            list1.add( sysDictTypeToDictTypePageVO( sysDictType ) );
        }

        return list1;
    }
}
