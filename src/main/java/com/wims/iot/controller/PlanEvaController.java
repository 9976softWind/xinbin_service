package com.wims.iot.controller;

import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResult;
import com.wims.iot.model.form.EvaDataForm;
import com.wims.iot.model.vo.PlanEvaVo;
import com.wims.iot.service.IPlanEvaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 预案评估表 前端控制器
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
@RestController
@RequestMapping("/v1/plans/evaluation")
public class PlanEvaController {

    @Autowired
    IPlanEvaService planEvaService;

    /**
     * 查看评估报告
     * @param executionId 执行实例 ID
     * @return
     */
    @GetMapping("/{executionId}")
    public KgpResult<PlanEvaVo> getPlanFileEva(@PathVariable String executionId){
        try {
            return KgpResult.success(planEvaService.getPlanFileEva(executionId));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }

    }

    /**
     * 生成评估报告
     * @param executionId   要生成评估报告的执行实例 ID
     * @return
     */
    @PostMapping("/{executionId}")
    public KgpResult<Boolean> addPlanFileEva(@PathVariable String executionId){

        try {
            return KgpResult.judge(planEvaService.addPlanFileEva(executionId));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }

    }

    /**
     * 提交评估结果
     * @param executionId
     * @param evaData
     * @return
     */
    @PutMapping("/{executionId}")
    public KgpResult<Boolean> setPlanFileEva(@PathVariable String executionId,@RequestBody EvaDataForm evaData){
        try {
            return KgpResult.judge(planEvaService.setPlanFileEva(executionId,evaData));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }
}
