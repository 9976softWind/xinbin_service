package com.wims.iot.model.query;

import com.wims.iot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FilePageQuery extends BasePageQuery {

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @Schema(description="目录id")
    private Long cataId;

}
