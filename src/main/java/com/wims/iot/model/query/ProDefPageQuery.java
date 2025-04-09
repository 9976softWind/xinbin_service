package com.wims.iot.model.query;

import com.wims.iot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 数据源分页查询对象
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Schema(description ="工作流分页查询对象")
@Data
public class ProDefPageQuery extends BasePageQuery {

    @Schema(description="工作流名称")
    private String processName;

}
