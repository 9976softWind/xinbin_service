package com.wims.iot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.entity.PlanCategory;

/**
 * <p>
 * 预案类别表 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
public interface IPlanCategoryService extends IService<PlanCategory> {

    PlanCategory addPlanCategory(String name, String description);

    PlanCategory setPlanCategory(String categoryId, PlanCategory planCategory);
}
