package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 预案文件表
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
@Getter
@Setter
@TableName("t_plan_file")
public class PlanFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    private String id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 引用的知识库文件主键id（col_file的file_id字段）
     */
    private String fileId;

    /**
     * 所属目录id（引用t_plan_directory的id字段）
     */
    private String directoryId;

    /**
     * 文件描述
     */
    private String description;


    /**
     * 预案优先级
     */
    private Integer preplanPriority;

    /**
     * 灾害类型
     */
    private Integer disasterType;

    /**
     * 适用地区
     */
    private String applicableArea;

    /**
     * 文件关联的预案主体类别Id（t_plan_category的category_id字段）
     */
    private String entityCategoryId;

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
