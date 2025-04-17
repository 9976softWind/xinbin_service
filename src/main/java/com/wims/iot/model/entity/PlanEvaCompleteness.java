package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 预案完整性评估
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
@Getter
@Setter
@TableName("t_plan_eva_completeness")
public class PlanEvaCompleteness implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id(引用t_plan_eva的completeness_id)
     */
    private String completenessId;

    /**
     * 目标设定得分
     */
    private Integer goalSettingPoints;

    /**
     * 风险识别得分
     */
    private Integer riskIdentificationPoints;

    /**
     * 应对措施得分
     */
    private Integer copingMeasuresPoints;

    /**
     * 资源配置得分
     */
    private Integer resourceAllocationPoints;

    /**
     * 责任分工得分
     */
    private Integer responsibilityDivisionPoints;

    /**
     * 完整性总得分
     */
    private Integer totalPoints;


}
