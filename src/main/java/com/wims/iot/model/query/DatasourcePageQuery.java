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
@Schema(description ="数据源分页查询对象")
@Data
public class DatasourcePageQuery extends BasePageQuery {

    @Schema(description="数据源名称")
    private String datasourceName;

    @Schema(description="数据源类型")
    private Integer datasourceType;


}
