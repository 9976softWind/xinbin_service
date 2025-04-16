package com.wims.iot.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description ="预案主体分页查询对象")
@Data
public class PlanCategoryQuery {

    @Schema(description="预案目录名称")
    private String name;

    @Schema(description = "页码", required = true, example = "1")
    private Integer page = 1;

    @Schema(description = "每页数量，默认20", required = true, example = "20")
    private Integer pageSize = 20;

}
