package com.wims.iot.controller;

import cn.hutool.core.util.ObjectUtil;
import com.wims.iot.common.result.KgpResult;
import com.wims.iot.common.result.KgpResultCode;
import com.wims.iot.model.entity.PlanCategory;
import com.wims.iot.service.IPlanCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 预案类别表 前端控制器
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
@RestController
@RequestMapping("/v1/plans/entityCategories")
public class PlanCategoryController {

    @Autowired
    IPlanCategoryService planCategoriesService;


    /**
     * 新增预案类别（主题）
     * @param name 类别名称，唯一
     * @param description 类别描述
     * @return
     */
    @PostMapping
    public KgpResult<PlanCategory> addPlanCategory(@RequestParam(value = "name",required = true) String name,
                                                   @RequestParam(value = "description",required = false) String description){
        PlanCategory insertResult = planCategoriesService.addPlanCategory(name,description);
        return ObjectUtil.isNull(insertResult) ? KgpResult.failed(KgpResultCode.SYSTEM_EXECUTION_ERROR) : KgpResult.success(insertResult);
    }

    /**
     * 修改现有预案类别的名称或描述
     * @param categoryId
     * @param planCategory
     * @return
     */
    @PutMapping("/{categoryId}")
    public KgpResult<PlanCategory> setPlanCategory(@PathVariable String categoryId, @RequestBody PlanCategory planCategory){
        PlanCategory updateResult = planCategoriesService.setPlanCategory(categoryId,planCategory);
        return ObjectUtil.isNull(updateResult) ? KgpResult.failed(KgpResultCode.SYSTEM_EXECUTION_ERROR) : KgpResult.success(updateResult);
    }

}
