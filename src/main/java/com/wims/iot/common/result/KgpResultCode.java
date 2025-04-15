package com.wims.iot.common.result;

import com.fasterxml.jackson.databind.node.ArrayNode;
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
public enum KgpResultCode implements  Serializable {


    VALIDATION_ERROR("VALIDATION_ERROR", "请求参数校验失败",null),

    DIRECTORY_NAME_CONFLICT("DIRECTORY_NAME_CONFLICT", "在父目录下已存在同名目录",null),

    DIRECTORY_NOT_FOUND("DIRECTORY_NOT_FOUND","指定的目录不存在",null),

    DIRECTORY_NOT_EMPTY("DIRECTORY_NOT_EMPTY", "目录不为空，无法删除。如需强制删除请设置 force=true",null),

    SYSTEM_EXECUTION_ERROR("SYSTEM_EXECUTION_ERROR", "操作失败",null);


    public String getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }

    private String code;

    private String message;

    private ArrayNode details;


    @Override
    public String toString() {
        return "KgpResultCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", details=" + details +
                '}';
    }

    public static KgpResultCode getValue(String code){
        for (KgpResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_EXECUTION_ERROR; // 默认系统执行错误
    }
}
