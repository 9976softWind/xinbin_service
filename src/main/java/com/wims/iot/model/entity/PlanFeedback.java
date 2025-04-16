package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 预案反馈表
 * </p>
 *
 * @author tdw
 * @since 2025-04-16
 */
@Getter
@Setter
@TableName("t_plan_feedback")
public class PlanFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 预案执行实例Id（t_preplan_exe的id）
     */
    private String exeId;

    /**
     * 反馈类型(progress, risk, effect, dispatch_status)
     */
    private String feedType;

    /**
     * 反馈的具体内容
     */
    private String content;

    /**
     * 更新执行实例的状态
     */
    private String exeStatus;

    /**
     * 关联的附件Id
     */
    private String releatedFileIds;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date feedBackTime;


}
