package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 预案类别字段表
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
@Getter
@Setter
@TableName("t_plan_category_field")
public class PlanCategoryField implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 预案类别Id（引用自t_plan_categories表的id字段）
     */
    private String planCategoryId;

    /**
     * 字段Key
     */
    private String name;

    /**
     * 字段显示名称
     */
    private String label;

    /**
     * 字段类型(text, number, date, select, multiselect, etc)
     */
    private String type;

    /**
     * 字段是否必需(0：非必需；1：必需)
     */
    private Integer required;

    /**
     * 下拉选项（仅当type为select/multiselect，其余为null）
     */
    private String options;

    /**
     * 显示顺序
     */
    private Integer order;

    /**
     * 字段描述或提示信息
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;


}
