package com.wims.iot.controller;

import cn.hutool.core.util.ObjectUtil;
import com.wims.iot.common.result.KgpResult;
import com.wims.iot.common.result.KgpResultCode;
import com.wims.iot.model.entity.PlanField;
import com.wims.iot.model.vo.PlanFieldVo;
import com.wims.iot.service.IPlanFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 预案类别字段表 前端控制器
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
@RestController
@RequestMapping("/v1/plans/entityCategories")
public class PlanFieldController {

    @Autowired
    IPlanFieldService planFieldService;

    /**
     * 向指定的预案类别中添加新的自定义字段
     * @param categoryId    类别 ID
     * @param planField     字段定义对象
     * @return
     */
    @PostMapping("/{categoryId}/fields")
    public KgpResult<PlanFieldVo> addPlanField(@PathVariable String categoryId, @RequestBody PlanField planField){
        PlanFieldVo insertResult = planFieldService.addPlanField(categoryId,planField);
        return ObjectUtil.isNull(insertResult) ? KgpResult.failed(KgpResultCode.SYSTEM_EXECUTION_ERROR) : KgpResult.success(insertResult);
    }

    /**
     *  查询指定预案类别下定义的所有字段信息。
     * @param categoryId 例：cat_fishing_port_flood
     * @return
     */
    @GetMapping("/{categoryId}/fields")
    public KgpResult<List<PlanField>> getPlanFields(@PathVariable String categoryId){
        List<PlanField> insertResult = planFieldService.getPlanFields(categoryId);
        return ObjectUtil.isNull(insertResult) ? KgpResult.failed(KgpResultCode.SYSTEM_EXECUTION_ERROR) : KgpResult.success(insertResult);
    }

}
