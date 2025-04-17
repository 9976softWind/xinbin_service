package com.wims.iot.service;

import com.wims.iot.model.entity.PlanEvaRisk;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 预案风险评估 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
public interface IPlanEvaRiskService extends IService<PlanEvaRisk> {

    void addEvaPoints(String riskId);

    PlanEvaRisk setEvaPoints(String riskId, List<Integer> riskRating);
}
