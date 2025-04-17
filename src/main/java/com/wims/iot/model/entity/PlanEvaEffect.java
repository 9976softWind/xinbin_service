package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 预案成效评估
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
@Getter
@Setter
@TableName("t_plan_eva_effect")
public class PlanEvaEffect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id(引用t_plan_eva的effect_id)
     */
    private String effectId;

    /**
     * 灾害损失控制程度得分
     */
    private Integer disasterLossControlPointsPoints;

    /**
     * 应急响应速度得分
     */
    private Integer emergencyResponseSpeedPoints;

    /**
     * 应急响应效率得分
     */
    private Integer emergencyResponseEfficiencyPoints;

    /**
     * 公众满意度得分
     */
    private Integer publicSatisfactionPoints;

    /**
     * 成效总得分
     */
    private Integer totalPoints;


}
