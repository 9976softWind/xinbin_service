package com.wims.iot.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author tdw
 * @date 2025.4.15
 */
@Data
public class PlanFieldVo {

    /**
     * 字段id(取自表的field_id)字段
     */
    private String id;

    /**
     * 字段内部名称/Key，类别内唯一
     */
    private String name;

    /**
     * 字段显示名称
     */
    private String label;

    /**
     * 字段类型（`text`, `number`, `date`, `select`等）
     */
    private String type;

    /**
     * 是否必填
     */
    private Integer required;

    /**
     * 选项列表 (当 `type` 为 `select` 等时需要)
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
    private Date createAt;


}
