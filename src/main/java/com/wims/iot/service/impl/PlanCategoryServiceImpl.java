package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.mapper.PlanCategoryMapper;
import com.wims.iot.model.entity.PlanCategory;
import com.wims.iot.model.vo.PlanCategoryVo;
import com.wims.iot.service.IPlanCategoryService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 预案类别表 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
@Service
public class PlanCategoryServiceImpl extends ServiceImpl<PlanCategoryMapper, PlanCategory> implements IPlanCategoryService {

    @Override
    public PlanCategoryVo addPlanCategory(String name, String description) {
        PlanCategory planCategory = new PlanCategory();
        planCategory.setCategoryId("cat_" + RandomStringGenerator.generate(6));
        planCategory.setName(name);
        if(!StringUtils.isNullOrEmpty(description)){
            planCategory.setDescription(description);
        }
        planCategory.setCreateAt(new Date());
        int insert = this.baseMapper.insert(planCategory);
        PlanCategoryVo planCategoryVo = new PlanCategoryVo();
        planCategoryVo.setId(planCategory.getCategoryId());
        planCategoryVo.setName(planCategory.getName());
        planCategoryVo.setDescription(planCategory.getDescription());
        planCategoryVo.setCreateAt(planCategory.getCreateAt());
        planCategoryVo.setUpdateAt(planCategory.getCreateAt());
        return insert == 1 ? planCategoryVo : null;
    }

    @Override
    public PlanCategoryVo setPlanCategory(String categoryId, PlanCategory planCategory) {
        planCategory.setUpdateAt(new Date());
        int update = this.baseMapper.update(planCategory, new QueryWrapper<PlanCategory>().eq("id", categoryId));
        PlanCategoryVo planCategoryVo = null;
        if( update == 1 ){
            PlanCategory newPlanCategory = this.baseMapper.selectById(categoryId);
            planCategoryVo = new PlanCategoryVo();
            planCategoryVo.setId(newPlanCategory.getCategoryId());
            planCategoryVo.setName(newPlanCategory.getName());
            planCategoryVo.setDescription(newPlanCategory.getDescription());
            planCategoryVo.setCreateAt(newPlanCategory.getCreateAt());
            planCategoryVo.setUpdateAt(newPlanCategory.getCreateAt());
        }
        return planCategoryVo;
    }

    @Override
    public PlanCategory getPlanCateGory(String categoryId) {
        return this.baseMapper.selectOne(new QueryWrapper<PlanCategory>().eq("category_id",categoryId));
    }

}
