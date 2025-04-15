package com.wims.iot.controller;

import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResult;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.service.IPlanFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 预案文件表 前端控制器
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
@RestController
@RequestMapping("/v1/plans/files")
public class PlanFileController {

    @Autowired
    IPlanFileService planFileService;

    @PostMapping
    public KgpResult<Boolean> addPlanFile(@RequestBody PlanFile planFile){
        try {
            return KgpResult.judge(planFileService.addPlanFile(planFile));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

    @DeleteMapping("/{id}")
    public KgpResult<Boolean> deletePlanFile(@PathVariable String id){
        try {
            return  KgpResult.judge(planFileService.deletePlanFile(id)) ;
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

    @PutMapping("/{id}")
    public KgpResult<Boolean> setPlanFileBasicInfo(@PathVariable String id,@RequestBody PlanFile planFile){
        try {
            return KgpResult.judge(planFileService.setPlanFileBasicInfo(id,planFile)) ;
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

    @PutMapping("/{id}/entityAttributes")
    public KgpResult<Boolean> setPlanFileEntityInfo(@PathVariable String id,@RequestBody String entityInfo){
        try {
            return KgpResult.judge(planFileService.setPlanFileEntityInfo(id,entityInfo)) ;
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

}
