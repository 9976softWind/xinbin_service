package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResultCode;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.mapper.PlanCategoryMapper;
import com.wims.iot.model.entity.PlanCategory;
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
    public Boolean addPlanCategory(String name, String description) {
        if(hasReName(name)){
            throw new KGBusinessException(KgpResultCode.CATEGORY_NAME_CONFLICT);
        }
        PlanCategory planCategory = new PlanCategory();
        planCategory.setCategoryId("cat_" + RandomStringGenerator.generate(6));
        planCategory.setName(name);
        if(!StringUtils.isNullOrEmpty(description)){
            planCategory.setDescription(description);
        }
        planCategory.setCreateAt(new Date());
        return this.baseMapper.insert(planCategory) == 1 ? true : false;
    }


    @Override
    public Boolean setPlanCategory(String categoryId, PlanCategory planCategory) {
        if(hasReName(planCategory.getName())){
            throw new KGBusinessException(KgpResultCode.CATEGORY_NAME_CONFLICT);
        }
        planCategory.setUpdateAt(new Date());
        return this.baseMapper.update(planCategory,new QueryWrapper<PlanCategory>().eq("category_id",categoryId)) == 1;
    }

    @Override
    public PlanCategory getPlanCateGory(String categoryId) {
        return this.baseMapper.selectOne(new QueryWrapper<PlanCategory>().eq("category_id",categoryId));
    }


    private boolean hasReName(String name) {
        return this.baseMapper.exists(new QueryWrapper<PlanCategory>().eq("name",name));
    }

}
