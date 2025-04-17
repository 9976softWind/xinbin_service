package com.wims.iot.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class PlanFileQuery {

    private List<String> ids;

    @Schema(description = "页码", required = true, example = "1")
    private Integer page = 1;

    @Schema(description = "每页数量，默认20", required = true, example = "20")
    private Integer pageSize = 20;

}
