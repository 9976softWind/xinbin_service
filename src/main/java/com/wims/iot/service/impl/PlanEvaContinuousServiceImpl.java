package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wims.iot.common.util.TotalPointsCacUtil;
import com.wims.iot.mapper.PlanEvaContinuousMapper;
import com.wims.iot.model.entity.PlanEvaContinuous;
import com.wims.iot.service.IPlanEvaContinuousService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 预案连续性评估 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
@Service
public class PlanEvaContinuousServiceImpl extends ServiceImpl<PlanEvaContinuousMapper, PlanEvaContinuous> implements IPlanEvaContinuousService {

    @Override
    public void addEvaPoints(String continuousId) {
        PlanEvaContinuous planEvaContinuous = new PlanEvaContinuous();
        planEvaContinuous.setContinuousId(continuousId);
        this.baseMapper.insert(planEvaContinuous);
    }

    @Override
    public PlanEvaContinuous setEvaPoints(String continuousId, List<Integer> continuityRating) {
        PlanEvaContinuous planEvaContinuous = new PlanEvaContinuous();
        planEvaContinuous.setTimeContinuityPoints(continuityRating.get(0));
        planEvaContinuous.setSpatialContinuityPoints(continuityRating.get(1));
        planEvaContinuous.setTotalPoints((int) Math.round(TotalPointsCacUtil.cacTotalPoints(continuityRating)));
        if(this.baseMapper.update(planEvaContinuous,new QueryWrapper<PlanEvaContinuous>().eq("continuous_id",continuousId)) == 1){
            return planEvaContinuous;
        }else{
            return null;
        }
    }
}
