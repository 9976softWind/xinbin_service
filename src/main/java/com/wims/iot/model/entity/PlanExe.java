package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 预案执行表
 * </p>
 *
 * @author tdw
 * @since 2025-04-16
 */
@Getter
@Setter
@TableName("t_plan_exe")
public class PlanExe implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 预案文件Id（t_plan_file的id字段）
     */
    private String planFileId;

    /**
     * 预案文件名称
     */
    private String planFileName;

    /**
     * 预案状态
     */
    private String status;

    /**
     * 执行主体信息
     */
    private String executor;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 上次反馈时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastFeedBackTime;


}
