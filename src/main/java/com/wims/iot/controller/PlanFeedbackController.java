package com.wims.iot.controller;

import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResult;
import com.wims.iot.model.entity.PlanFeedback;
import com.wims.iot.service.IPlanFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 预案反馈表 前端控制器
 * </p>
 *
 * @author tdw
 * @since 2025-04-16
 */
@RestController
@RequestMapping("/v1/plans/feedback")
public class PlanFeedbackController {

    @Autowired
    IPlanFeedbackService planFeedbackService;

    /**
     * 预案反馈
     * 提交关于某个预案执行实例的反馈信息（进度、风险、效果、调度状态等)
     * @param executionId       预案执行实例 ID
     * @param planFeedback      反馈实体
     * @return
     */
    @PostMapping("/{executionId}")
    public KgpResult<Boolean> planExeFeedBack(@PathVariable String executionId, @RequestBody PlanFeedback planFeedback){
        try {
            return KgpResult.judge(planFeedbackService.planExeFeedBack(executionId,planFeedback));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

}
