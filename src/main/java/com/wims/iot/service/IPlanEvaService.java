package com.wims.iot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.entity.PlanEva;
import com.wims.iot.model.form.EvaDataForm;
import com.wims.iot.model.vo.PlanEvaVo;

/**
 * <p>
 * 预案评估表 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
public interface IPlanEvaService extends IService<PlanEva> {

    Boolean addPlanFileEva(String executionId);

    PlanEvaVo getPlanFileEva(String executionId);

    Boolean setPlanFileEva(String executionId, EvaDataForm evaData);
}
