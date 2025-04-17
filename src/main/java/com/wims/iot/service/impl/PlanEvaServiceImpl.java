package com.wims.iot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResultCode;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.common.util.TotalPointsCacUtil;
import com.wims.iot.mapper.PlanEvaMapper;
import com.wims.iot.model.entity.*;
import com.wims.iot.model.form.EvaDataForm;
import com.wims.iot.model.vo.PlanEvaVo;
import com.wims.iot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 预案评估表 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
@Service
public class PlanEvaServiceImpl extends ServiceImpl<PlanEvaMapper, PlanEva> implements IPlanEvaService {

    @Autowired
    IPlanEvaCompletenessService planEvaCompletenessService;
    @Autowired
    IPlanEvaContinuousService planEvaContinuousService;
    @Autowired
    IPlanEvaEffectService planEvaEffectService;
    @Autowired
    IPlanEvaRiskService planEvaRiskService;

    @Override
    @Transactional
    public Boolean addPlanFileEva(String executionId) {
        if(hasRecord(executionId)){
            throw new KGBusinessException(KgpResultCode.EVA_RECORD_CONFLICT);
        }
        PlanEva planEva = new PlanEva();
        planEva.setExeId(executionId);
        planEva.setCompletenessId("completeness_" + RandomStringGenerator.generate(6));
        planEva.setContinuousId("continuous_" + RandomStringGenerator.generate(6));
        planEva.setEffectId("effect_" + RandomStringGenerator.generate(6));
        planEva.setRiskId("risk_" + RandomStringGenerator.generate(6));
        if(this.baseMapper.insert(planEva) == 1){
            planEvaCompletenessService.addEvaPoints(planEva.getCompletenessId());
            planEvaContinuousService.addEvaPoints(planEva.getContinuousId());
            planEvaEffectService.addEvaPoints(planEva.getEffectId());
            planEvaRiskService.addEvaPoints(planEva.getRiskId());
            return true;
        }else{
            throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
        }
    }

    @Override
    public PlanEvaVo getPlanFileEva(String executionId) {
        return this.baseMapper.getPlanFileEva(executionId);
    }

    @Override
    @Transactional
    public Boolean setPlanFileEva(String executionId, EvaDataForm evaData) {
        PlanEva planEva = this.baseMapper.selectOne(new QueryWrapper<PlanEva>().eq("exe_id", executionId));
        PlanEvaCompleteness planEvaCompleteness = planEvaCompletenessService.setEvaPoints(planEva.getCompletenessId(), evaData.getCompletenessRating());
        PlanEvaContinuous planEvaContinuous = planEvaContinuousService.setEvaPoints(planEva.getContinuousId(), evaData.getContinuityRating());
        PlanEvaEffect planEvaEffect = planEvaEffectService.setEvaPoints(planEva.getEffectId(), evaData.getEffectivenessRating());
        PlanEvaRisk planEvaRisk = planEvaRiskService.setEvaPoints(planEva.getRiskId(), evaData.getRiskRating());
        if(ObjectUtil.isNull(planEvaCompleteness) || ObjectUtil.isNull(planEvaContinuous) || ObjectUtil.isNull(planEvaEffect) || ObjectUtil.isNull(planEvaRisk)){
            throw new KGBusinessException(KgpResultCode.EVA_POINTS_ERROR);
        }
        List<Integer> arrayList = new ArrayList();
        arrayList.add(planEvaCompleteness.getTotalPoints());
        arrayList.add(planEvaContinuous.getTotalPoints());
        arrayList.add(planEvaEffect.getTotalPoints());
        arrayList.add(planEvaRisk.getTotalPoints());
        planEva.setTotalPoints((int) Math.round(TotalPointsCacUtil.cacTotalPoints(arrayList)));
        if(!StringUtils.isNullOrEmpty(evaData.getComments())){
            planEva.setComments(evaData.getComments());
        }
        if(!StringUtils.isNullOrEmpty(evaData.getEvaluator())){
            planEva.setEvaluator(evaData.getEvaluator());
        }
        planEva.setEvaluatedAt(new Date());
        return this.baseMapper.update(planEva,new QueryWrapper<PlanEva>().eq("exe_id",executionId)) == 1;
    }

    private boolean hasRecord(String executionId) {
        return this.baseMapper.exists(new QueryWrapper<PlanEva>().eq("exe_id", executionId));
    }




}
