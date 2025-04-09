package com.wims.iot.model.query;

import com.wims.iot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lichen
 * @date 2023/12/13/0013 16:44
 */
@Data
public class StationPageQuery extends BasePageQuery {

    @ApiModelProperty(value = "测站名称")
    private String stName;

    @ApiModelProperty(value = "测站编号 (对接水文站点编号)")
    private String stcd;

    @ApiModelProperty(value = "站点类型   DD--堰闸，DP--泵站，ZZ--河道，RR--水库，PP--雨量，TT—潮位站， WQ—水质站，BB-蒸发站，SC-智慧井盖、VP-视频站 ")
    private String sttp;

    @ApiModelProperty(value = "部门id")
    private Long deptId;

}
