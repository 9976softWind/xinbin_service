package com.wims.iot.common.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应码枚举
 *
 * @author haoxr
 * @since 2020-06-23
 **/
@AllArgsConstructor
@NoArgsConstructor
public enum UserCode implements IResultCode, Serializable {

    SUCCESS("00000", "一切ok"),

    USER_NOT_EXIST("U0001", "yonghu"),
    SYSTEM_EXECUTION_ERROR("B0001", "系统执行出错"),
    ;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                '}';
    }


    public static UserCode getValue(String code){
        for (UserCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_EXECUTION_ERROR; // 默认系统执行错误
    }
}
