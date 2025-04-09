package com.wims.iot.common.constant;

/**
 * @author lichen
 * @date 2023/7/13 15:35
 */
public class Constants {

    /**
     * 传输协议：tcp/udp protocol
     */
    public interface TransportProtocol {
        String TCP = "TCP";
        String UDP = "UDP";
    }

    /**
     * 设备管理--黑白名单选择：0-未启用，1-黑名单，2-白名单
     */
    public interface BlackWhiteListChoose {
        Integer NOT_ENABLED = 0;
        public Integer BLACKLIST = 1;
        public Integer WHITELIST = 2;
    }
}
