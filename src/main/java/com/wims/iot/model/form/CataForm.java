package com.wims.iot.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "目录表单对象")
@Data
public class CataForm {

    @Schema(description="目录ID")
    private Long id;

    @Schema(description="目录名称")
    private String name;

    @Schema(description="父目录ID")
    private Long parentId;

    @Schema(description="状态(1:启用;0:禁用)")
    private Integer status;

    @Schema(description="排序(数字越小排名越靠前)")
    private Integer sort;

}
