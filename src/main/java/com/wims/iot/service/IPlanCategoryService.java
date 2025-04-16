package com.wims.iot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.entity.PlanCategory;
import com.wims.iot.model.query.PlanCategoryQuery;

/**
 * <p>
 * 预案类别表 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
public interface IPlanCategoryService extends IService<PlanCategory> {

    Boolean addPlanCategory(String name, String description);

    Boolean setPlanCategory(String categoryId, PlanCategory planCategory);

    PlanCategory getPlanCateGory(String categoryId);

    Boolean deletePlanCategory(String categoryId, Boolean force);

    IPage<PlanCategory> getPlanCategoryList(PlanCategoryQuery query);
}
