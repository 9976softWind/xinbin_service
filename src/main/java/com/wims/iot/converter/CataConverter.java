package com.wims.iot.converter;

import com.wims.iot.model.entity.Cata;
import com.wims.iot.model.form.CataForm;
import com.wims.iot.model.vo.CataVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CataConverter {

    CataForm entity2Form(Cata entity);

    CataVO entity2Vo(Cata entity);

    Cata form2Entity(CataForm deptForm);

}
