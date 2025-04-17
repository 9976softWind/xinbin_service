package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wims.iot.common.util.TotalPointsCacUtil;
import com.wims.iot.mapper.PlanEvaEffectMapper;
import com.wims.iot.model.entity.PlanEvaEffect;
import com.wims.iot.service.IPlanEvaEffectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 预案成效评估 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
@Service
public class PlanEvaEffectServiceImpl extends ServiceImpl<PlanEvaEffectMapper, PlanEvaEffect> implements IPlanEvaEffectService {

    @Override
    public void addEvaPoints(String effectId) {
        PlanEvaEffect planEvaEffect = new PlanEvaEffect();
        planEvaEffect.setEffectId(effectId);
        this.baseMapper.insert(planEvaEffect);
    }

    @Override
    public PlanEvaEffect setEvaPoints(String effectId, List<Integer> effectivenessRating) {
        PlanEvaEffect planEvaEffect = new PlanEvaEffect();
        planEvaEffect.setDisasterLossControlPointsPoints(effectivenessRating.get(0));
        planEvaEffect.setEmergencyResponseSpeedPoints(effectivenessRating.get(1));
        planEvaEffect.setEmergencyResponseEfficiencyPoints(effectivenessRating.get(2));
        planEvaEffect.setPublicSatisfactionPoints(effectivenessRating.get(3));
        planEvaEffect.setTotalPoints((int) Math.round(TotalPointsCacUtil.cacTotalPoints(effectivenessRating)));
        if(this.baseMapper.update(planEvaEffect, new QueryWrapper<PlanEvaEffect>().eq("effect_id", effectId)) == 1){
            return planEvaEffect;
        }else{
            return null;
        }
    }
}
