package com.wims.iot.model.vo;

import com.wims.iot.model.entity.*;
import lombok.Data;

/**
 * @author tdw
 * @date 2025.4.15
 */
@Data
public class PlanEvaVo {

//    private String id;

    private PlanEva planEva;

    private PlanEvaCompleteness planEvaCompleteness;

    private PlanEvaContinuous planEvaContinuous;

    private PlanEvaEffect planEvaEffect;

    private PlanEvaRisk planEvaRisk;

}
