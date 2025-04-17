package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wims.iot.common.util.TotalPointsCacUtil;
import com.wims.iot.model.entity.PlanEvaCompleteness;
import com.wims.iot.mapper.PlanEvaCompletenessMapper;
import com.wims.iot.service.IPlanEvaCompletenessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 预案完整性评估 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
@Service
public class PlanEvaCompletenessServiceImpl extends ServiceImpl<PlanEvaCompletenessMapper, PlanEvaCompleteness> implements IPlanEvaCompletenessService {

    @Override
    public void addEvaPoints(String completenessId) {
        PlanEvaCompleteness planEvaCompleteness = new PlanEvaCompleteness();
        planEvaCompleteness.setCompletenessId(completenessId);
        this.baseMapper.insert(planEvaCompleteness);
    }

    @Override
    public PlanEvaCompleteness setEvaPoints(String completenessId, List<Integer> completenessRating) {
        PlanEvaCompleteness planEvaCompleteness = new PlanEvaCompleteness();
        planEvaCompleteness.setGoalSettingPoints(completenessRating.get(0));
        planEvaCompleteness.setRiskIdentificationPoints(completenessRating.get(1));
        planEvaCompleteness.setCopingMeasuresPoints(completenessRating.get(2));
        planEvaCompleteness.setResourceAllocationPoints(completenessRating.get(3));
        planEvaCompleteness.setResponsibilityDivisionPoints(completenessRating.get(4));
        planEvaCompleteness.setTotalPoints((int) Math.round(TotalPointsCacUtil.cacTotalPoints(completenessRating)));
        if ( this.baseMapper.update(planEvaCompleteness,new QueryWrapper<PlanEvaCompleteness>().eq("completeness_id",completenessId)) == 1){
            return planEvaCompleteness;
        }else{
            return null;
        }
    }
}
