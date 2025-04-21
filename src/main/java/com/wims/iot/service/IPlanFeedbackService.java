package com.wims.iot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.entity.PlanFeedback;

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
