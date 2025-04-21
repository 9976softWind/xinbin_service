package com.wims.iot.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description ="预案目录分页查询对象")
@Data
public class PlanFileExeFeedBackQuery {

    @Schema(description="预案文件 ID")
    private String planFileId;

    @Schema(description="执行状态：pending,running,completed,failed")
    private String status;


    @Schema(description="开始日期")
    private String startTime;

    @Schema(description="结束日期")
    private String endTime;

    @Schema(description = "页码", required = true, example = "1")
    private int page = 1;

    @Schema(description = "每页数量，默认20", required = true, example = "20")
    private int pageSize = 20;

}
