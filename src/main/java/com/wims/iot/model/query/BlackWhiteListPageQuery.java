package com.wims.iot.model.query;

import com.wims.iot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lichen
 * @date 2023/12/15/0015 16:41
 */
@Data
public class BlackWhiteListPageQuery extends BasePageQuery {

    @ApiModelProperty(value = "0-黑名单，1-白名单")
    @NotNull
    private Integer type;

    @ApiModelProperty(value = "设备id号")
    private String eqCode;

    @ApiModelProperty(value = "设备名")
    private String eqName;

    @ApiModelProperty(value = "站点id")
    private Integer stId;

    @ApiModelProperty(value = "在线状态   0-不在线 1-在线")
    private Integer isOnline;
}
