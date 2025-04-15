package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 预案类别字段关系映射表
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
@Getter
@Setter
@TableName("t_category_field")
public class CategoryField implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 预案类别Id（引用自t_plan_category表的id字段）
     */
    private Integer categoryId;

    /**
     * 预案类别字段Id（引用自t_plan_field表的id字段）
     */
    private Integer fieldId;


}
