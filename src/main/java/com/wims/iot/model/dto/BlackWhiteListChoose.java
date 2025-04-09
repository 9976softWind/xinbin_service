package com.wims.iot.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 黑白名单选择
 * @author lichen
 * @date 2023/12/14/0014 11:44
 */
@Builder
@Data
public class BlackWhiteListChoose {

    /**
     * 0-未启用，1-黑名单，2-白名单
     */
    private Integer blackWhiteListChoose;

    public BlackWhiteListChoose(Integer blackWhiteListChoose) {
        this.blackWhiteListChoose = blackWhiteListChoose;
    }
}
