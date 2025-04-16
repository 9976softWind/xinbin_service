package com.wims.iot.service;

import com.wims.iot.model.entity.PlanFeedback;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 预案反馈表 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-16
 */
public interface IPlanFeedbackService extends IService<PlanFeedback> {

    Boolean planExeFeedBack(String executionId, PlanFeedback planFeedback);
}
