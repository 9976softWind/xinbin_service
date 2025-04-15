package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 预案类别表
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
@Getter
@Setter
@TableName("t_plan_category")
public class PlanCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 预案id
     */
    private String id;

    /**
     * 预案类别名称
     */
    private String name;

    /**
     * 预案来源
     */
    private String source;

    /**
     * 预案类别描述
     */
    private String description;

    /**
     * 预案主体字段
     */
    private String fields;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;


}
