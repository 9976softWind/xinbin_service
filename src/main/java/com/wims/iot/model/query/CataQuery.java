package com.wims.iot.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description ="目录分页查询对象")
@Data
public class CataQuery {

    @Schema(description="关键字(目录名称)")
    private String keywords;

    @Schema(description="状态(1->正常；0->禁用)")
    private Integer status;

}
