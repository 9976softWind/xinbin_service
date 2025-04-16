package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResultCode;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.mapper.PlanFieldMapper;
import com.wims.iot.model.entity.PlanField;
import com.wims.iot.service.IPlanCategoryService;
import com.wims.iot.service.IPlanFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 预案类别字段表 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
@Service
public class PlanFieldServiceImpl extends ServiceImpl<PlanFieldMapper, PlanField> implements IPlanFieldService {


    @Autowired
    IPlanCategoryService planCategoryService;


    @Override
    @Transactional
    public Boolean addPlanField(String categoryId, PlanField planField) {
        if(hasReName(categoryId,planField.getName())){
            throw new KGBusinessException(KgpResultCode.FIELD_NAME_CONFLICT);
        }
        planField.setCategoryId(categoryId);
        planField.setFieldId("field_" + RandomStringGenerator.generate(6));
        planField.setCreatedAt(new Date());
        if(this.baseMapper.insert(planField) == 1){
            return true;
        }else{
            throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
        }
    }



    @Override
    public List<PlanField> getPlanFields(String categoryId) {
        return this.baseMapper.selectList(new QueryWrapper<PlanField>().eq("category_id", categoryId));
    }

    private boolean hasReName(String categoryId,String name) {
        return this.baseMapper.exists(new QueryWrapper<PlanField>().eq("category_id",categoryId).eq("name",name));
    }
}
