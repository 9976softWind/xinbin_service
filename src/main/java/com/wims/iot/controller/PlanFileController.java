package com.wims.iot.controller;

import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResult;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.service.IPlanFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public KgpResult<PlanFile> addPlanFile(@RequestBody PlanFile planFile){
        try {
            PlanFile insertResult = planFileService.addPlanFile(planFile);
            return KgpResult.success(insertResult);
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

}
