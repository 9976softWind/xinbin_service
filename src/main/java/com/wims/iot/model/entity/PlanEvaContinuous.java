package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 预案连续性评估
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
@Getter
@Setter
@TableName("t_plan_eva_continuous")
public class PlanEvaContinuous implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id(引用t_plan_eva的continuous_id)
     */
    private String continuousId;

    /**
     * 时间连续性得分
     */
    private Integer timeContinuityPoints;

    /**
     * 空间连续性得分
     */
    private Integer spatialContinuityPoints;

    /**
     * 连续性总得分
     */
    private Integer totalPoints;


}
