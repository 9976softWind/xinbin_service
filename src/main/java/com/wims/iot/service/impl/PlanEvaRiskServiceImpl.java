package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wims.iot.common.util.TotalPointsCacUtil;
import com.wims.iot.mapper.PlanEvaRiskMapper;
import com.wims.iot.model.entity.PlanEvaRisk;
import com.wims.iot.service.IPlanEvaRiskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 预案风险评估 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
@Service
public class PlanEvaRiskServiceImpl extends ServiceImpl<PlanEvaRiskMapper, PlanEvaRisk> implements IPlanEvaRiskService {

    @Override
    public void addEvaPoints(String riskId) {
        PlanEvaRisk planEvaRisk = new PlanEvaRisk();
        planEvaRisk.setRiskId(riskId);
        this.baseMapper.insert(planEvaRisk);
    }

    @Override
    public PlanEvaRisk setEvaPoints(String riskId, List<Integer> riskRating) {
        PlanEvaRisk planEvaRisk = new PlanEvaRisk();
        planEvaRisk.setResourceShortagePoints(riskRating.get(0));
        planEvaRisk.setPersonnelInjuryPoints(riskRating.get(1));
        planEvaRisk.setFacilityDamagePoints(riskRating.get(2));
        planEvaRisk.setPublicPanicPoints(riskRating.get(3));
        planEvaRisk.setTotalPoints((int) Math.round(TotalPointsCacUtil.cacTotalPoints(riskRating)));
        if(this.baseMapper.update(planEvaRisk, new QueryWrapper<PlanEvaRisk>().eq("risk_id", riskId)) == 1){
            return planEvaRisk;
        }else{
            return null;
        }
    }
}
