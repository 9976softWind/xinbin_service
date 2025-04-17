package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 预案风险评估
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
@Getter
@Setter
@TableName("t_plan_eva_risk")
public class PlanEvaRisk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id(引用t_plan_eva的risk_id)
     */
    private String riskId;

    /**
     * 资源短缺得分
     */
    private Integer resourceShortagePoints;

    /**
     * 人员受伤得分
     */
    private Integer personnelInjuryPoints;

    /**
     * 设施损坏得分
     */
    private Integer facilityDamagePoints;

    /**
     * 公众恐慌得分
     */
    private Integer publicPanicPoints;

    /**
     * 风险总得分
     */
    private Integer totalPoints;


}
