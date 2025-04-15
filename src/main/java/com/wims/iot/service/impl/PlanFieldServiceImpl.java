package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.mapper.PlanFieldMapper;
import com.wims.iot.model.entity.CategoryField;
import com.wims.iot.model.entity.PlanCategory;
import com.wims.iot.model.entity.PlanField;
import com.wims.iot.model.vo.PlanFieldVo;
import com.wims.iot.service.ICategoryFieldService;
import com.wims.iot.service.IPlanCategoryService;
import com.wims.iot.service.IPlanFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    ICategoryFieldService categoryFieldService;

    @Override
    public PlanFieldVo addPlanField(String categoryId, PlanField planField) {
        planField.setCategoryId(categoryId);
        planField.setFieldId("field_" + RandomStringGenerator.generate(6));
        planField.setCreatedAt(new Date());
        int insert = this.baseMapper.insert(planField);
        PlanFieldVo planFieldVo = null;
        if(insert == 1){
            CategoryField categoryField = new CategoryField();
            PlanCategory planCateGory = planCategoryService.getPlanCateGory(categoryId);
            categoryField.setCategoryId(planCateGory.getId());
            categoryField.setFieldId(planField.getId());
            if(categoryFieldService.addCategoryField(categoryField)){
                planFieldVo = new PlanFieldVo();
                planFieldVo.setId(planField.getCategoryId());
                planFieldVo.setName(planField.getName());
                planFieldVo.setLabel(planField.getLabel());
                planFieldVo.setType(planField.getType());
                planFieldVo.setRequired(planField.getRequired());
                planFieldVo.setOptions(planField.getOptions());
                planFieldVo.setOrder(planField.getOrder());
                planFieldVo.setDescription(planField.getDescription());
                planFieldVo.setCreateAt(planField.getCreatedAt());
            }
        }
        return planFieldVo;
    }

    @Override
    public List<PlanField> getPlanFields(String categoryId) {
        PlanCategory planCateGory = planCategoryService.getPlanCateGory(categoryId);
        List<Integer> fieldsByCategoryIds = categoryFieldService.getFieldsByCategoryId(planCateGory.getId());
        List<PlanField> planFields = this.baseMapper.selectList(new QueryWrapper<PlanField>().in("field_id", fieldsByCategoryIds));
        return planFields;
    }
}
