package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

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
     * 文件大小
     */
    private Long size;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 所属目录id（引用t_plan_directories的id字段）
     */
    private String directoryId;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 文件关联的预案主体类别Id（t_plan_categories的Id字段）
     */
    private String entityCategoryId;

    /**
     * 资源地址
     */
    private String url;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 上传人
     */
    private String uploaderId;

    private Integer disasterType;

    /**
     * 预案优先级
     */
    private Integer preplanPriority;

    /**
     * 适用地区
     */
    private String applicableArea;


}
