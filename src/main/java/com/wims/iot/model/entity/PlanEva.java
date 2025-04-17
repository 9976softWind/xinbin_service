package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 预案评估表
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
@Getter
@Setter
@TableName("t_plan_eva")
public class PlanEva implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 执行预案实例Id（t_plan_exe的id字段）
     */
    private String exeId;

    /**
     * 预案完整性评估
     */
    private String completenessId;

    /**
     * 预案连续性评估
     */
    private String continuousId;

    /**
     * 预案成效评估
     */
    private String effectId;

    /**
     * 预案风险评估
     */
    private String riskId;

    /**
     * 预案评估得分
     */
    private Integer totalPoints;

    /**
     * 评估意见或评语
     */
    private String comments;

    /**
     * 评估人信息
     */
    private String evaluator;

    /**
     * 评估时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date evaluatedAt;


}
