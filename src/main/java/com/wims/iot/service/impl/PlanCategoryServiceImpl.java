package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
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
 * @since 2025-04-14
 */
@Service
public class PlanCategoryServiceImpl extends ServiceImpl<PlanCategoryMapper, PlanCategory> implements IPlanCategoryService {

    @Override
    public PlanCategory addPlanCategory(String name, String description) {
        PlanCategory planCategory = new PlanCategory();
        planCategory.setId("cat_" + RandomStringGenerator.generate(6));
        planCategory.setName(name);
        if(!StringUtils.isNullOrEmpty(description)){
            planCategory.setDescription(description);
        }
        planCategory.setCreatedAt(new Date());
        int insert = this.baseMapper.insert(planCategory);
        return insert == 1 ? planCategory : null;
    }

    @Override
    public PlanCategory setPlanCategory(String categoryId, PlanCategory planCategory) {
        planCategory.setUpdatedAt(new Date());
        int update = this.baseMapper.update(planCategory, new QueryWrapper<PlanCategory>().eq("id", categoryId));
        return update == 1 ? this.baseMapper.selectById(categoryId) : null;
    }
}
