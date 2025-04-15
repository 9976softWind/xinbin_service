package com.wims.iot.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description ="预案目录分页查询对象")
@Data
public class PlanDirectoryQuery {

    @Schema(description="父目录id")
    private String parentId;

    @Schema(description="预案目录名称")
    private String name;

    @Schema(description="排序字段")
    private String sortBy;

    @Schema(description="排序顺序:asc,desc")
    private String sortOrder;

    @Schema(description = "页码", required = true, example = "1")
    private Integer page = 1;

    @Schema(description = "每页数量，默认20", required = true, example = "20")
    private Integer pageSize = 20;

}
