package com.wims.iot.service;

import com.wims.iot.model.entity.PlanEvaContinuous;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 预案连续性评估 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
public interface IPlanEvaContinuousService extends IService<PlanEvaContinuous> {

    void addEvaPoints(String continuousId);

    PlanEvaContinuous setEvaPoints(String continuousId, List<Integer> continuityRating);
}
