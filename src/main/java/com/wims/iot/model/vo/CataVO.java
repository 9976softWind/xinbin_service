package com.wims.iot.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "目录视图对象")
@Data
public class CataVO {

    @Schema(description = "目录ID")
    private Long id;

    @Schema(description = "父部门ID")
    private Long parentId;

    @Schema(description = "目录名称")
    private String name;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态(1:启用；0:禁用)")
    private Integer status;

    @Schema(description = "子部门")
    private List<CataVO> children;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;
    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;

}
