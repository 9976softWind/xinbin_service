package com.wims.iot.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgPageResult;
import com.wims.iot.common.result.KgpResult;
import com.wims.iot.model.entity.PlanCategory;
import com.wims.iot.model.query.PlanCategoryQuery;
import com.wims.iot.service.IPlanCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 预案类别表 前端控制器
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
@RestController
@RequestMapping("/v1/plans/entityCategories")
public class PlanCategoryController {

    @Autowired
    IPlanCategoryService planCategoryService;


    @GetMapping
    public KgPageResult<PlanCategory> getPlanCategoryList(@RequestParam(value = "name",required = false) String name,
                                                          @RequestParam(value = "page",required = true) Integer page,
                                                          @RequestParam(value = "pageSize",required = true) Integer pageSize){
        PlanCategoryQuery query = new PlanCategoryQuery();
        query.setName(name);
        query.setPage(page);
        query.setPageSize(pageSize);
        IPage<PlanCategory> result = planCategoryService.getPlanCategoryList(query);
        return KgPageResult.success(result);
    }


    /**
     * 新增预案类别（主体）
     * @param name 类别名称，唯一
     * @param description 类别描述
     * @return
     */
    @PostMapping
    public KgpResult<Boolean> addPlanCategory(@RequestParam(value = "name",required = true) String name,
                                                     @RequestParam(value = "description",required = false) String description){
        try {
            return KgpResult.judge(planCategoryService.addPlanCategory(name,description));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

    /**
     * 编辑类别名称
     * @param categoryId
     * @param planCategory
     * @return
     */
    @PutMapping("/{categoryId}")
    public KgpResult<Boolean> setPlanCategory(@PathVariable String categoryId, @RequestBody PlanCategory planCategory){
        try {
            return KgpResult.judge(planCategoryService.setPlanCategory(categoryId,planCategory));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

    /**
     * 删除不再需要的预案类别。默认情况下，如果类别已被文件关联，则不允许删除
     * @param categoryId 要删除的类别 ID
     * @param force 是否强制删除（即使有关联文件，谨慎使用）,默认False
     * @return
     */
    @DeleteMapping("/{categoryId}")
    public KgpResult<Boolean> deletePlanCategory(@PathVariable String categoryId,@RequestParam(value = "force") Boolean force){
        try {
            return KgpResult.judge(planCategoryService.deletePlanCategory(categoryId,force));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }
}
