package com.wims.iot.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wims.iot.model.entity.PlanField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author tdw
 * @date 2025.4.15
 */
@Data
public class PlanCategoryVo {

    /**
     * 预案类别id(取自表的category_id)字段
     */
    private String id;

    /**
     * 预案类别名称
     */
    private String name;

    /**
     * 预案类别描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    /**
     * 主体字段信息
     */
    private List<PlanField> fields;



}
