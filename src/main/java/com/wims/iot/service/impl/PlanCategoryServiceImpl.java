package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResultCode;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.mapper.PlanCategoryMapper;
import com.wims.iot.model.entity.PlanCategory;
import com.wims.iot.model.query.PlanCategoryQuery;
import com.wims.iot.service.IPlanCategoryService;
import com.wims.iot.service.IPlanFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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

    @Autowired
    IPlanFileService planFileService;

    @Override
    public IPage<PlanCategory> getPlanCategoryList(PlanCategoryQuery query) {
        Page<PlanCategory> page = new Page<>(query.getPage(), query.getPageSize());
        this.baseMapper.getPlanCategoryList(page,query);
        return page;
    }

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

    @Override
    public Boolean deletePlanCategory(String categoryId, Boolean force) {
        boolean hasFiles = planFileService.hasRelevanceFiles(categoryId);
        boolean isForce = Optional.ofNullable(force).orElse(false);
        // 非强制删除且存在关联文件时，拒绝删除
        if (!isForce && hasFiles) {
            throw new KGBusinessException(KgpResultCode.CATEGORY_IN_USE);
        }
        // 执行删除（强制删除 或 非强制但无关联文件）
        return this.baseMapper.delete(new QueryWrapper<PlanCategory>().eq("category_id",categoryId)) == 1;
    }

    /**
     * 判断预案类别名称是否重复
     * @param name
     * @return
     */
    private boolean hasReName(String name) {
        return this.baseMapper.exists(new QueryWrapper<PlanCategory>().eq("name",name));
    }

}
