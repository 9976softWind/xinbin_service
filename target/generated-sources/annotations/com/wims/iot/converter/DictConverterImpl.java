package com.wims.iot.converter;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wims.iot.model.entity.SysDict;
import com.wims.iot.model.form.DictForm;
import com.wims.iot.model.vo.DictPageVO;
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
public class DictConverterImpl implements DictConverter {

    @Override
    public Page<DictPageVO> entity2Page(Page<SysDict> page) {
        if ( page == null ) {
            return null;
        }

        Page<DictPageVO> page1 = new Page<DictPageVO>();

        page1.setPages( page.getPages() );
        page1.setRecords( sysDictListToDictPageVOList( page.getRecords() ) );
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
    public DictForm entity2Form(SysDict entity) {
        if ( entity == null ) {
            return null;
        }

        DictForm dictForm = new DictForm();

        dictForm.setId( entity.getId() );
        dictForm.setTypeCode( entity.getTypeCode() );
        dictForm.setName( entity.getName() );
        dictForm.setValue( entity.getValue() );
        dictForm.setStatus( entity.getStatus() );
        dictForm.setSort( entity.getSort() );
        dictForm.setRemark( entity.getRemark() );

        return dictForm;
    }

    @Override
    public SysDict form2Entity(DictForm entity) {
        if ( entity == null ) {
            return null;
        }

        SysDict sysDict = new SysDict();

        sysDict.setId( entity.getId() );
        sysDict.setTypeCode( entity.getTypeCode() );
        sysDict.setName( entity.getName() );
        sysDict.setValue( entity.getValue() );
        sysDict.setSort( entity.getSort() );
        sysDict.setStatus( entity.getStatus() );
        sysDict.setRemark( entity.getRemark() );

        return sysDict;
    }

    protected DictPageVO sysDictToDictPageVO(SysDict sysDict) {
        if ( sysDict == null ) {
            return null;
        }

        DictPageVO dictPageVO = new DictPageVO();

        dictPageVO.setId( sysDict.getId() );
        dictPageVO.setName( sysDict.getName() );
        dictPageVO.setValue( sysDict.getValue() );
        dictPageVO.setStatus( sysDict.getStatus() );

        return dictPageVO;
    }

    protected List<DictPageVO> sysDictListToDictPageVOList(List<SysDict> list) {
        if ( list == null ) {
            return null;
        }

        List<DictPageVO> list1 = new ArrayList<DictPageVO>( list.size() );
        for ( SysDict sysDict : list ) {
            list1.add( sysDictToDictPageVO( sysDict ) );
        }

        return list1;
    }
}
