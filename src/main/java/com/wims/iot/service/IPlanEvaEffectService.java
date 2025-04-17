package com.wims.iot.service;

import com.wims.iot.model.entity.PlanEvaEffect;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 预案成效评估 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
public interface IPlanEvaEffectService extends IService<PlanEvaEffect> {

    void addEvaPoints(String effectId);

    PlanEvaEffect setEvaPoints(String effectId, List<Integer> effectivenessRating);
}
