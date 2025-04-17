package com.wims.iot.service;

import com.wims.iot.model.entity.PlanEvaCompleteness;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 预案完整性评估 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
public interface IPlanEvaCompletenessService extends IService<PlanEvaCompleteness> {

    void addEvaPoints(String completenessId);

    PlanEvaCompleteness setEvaPoints(String completenessId, List<Integer> completenessRating);
}
