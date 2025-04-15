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
public enum KgpResultCode implements IResultCode, Serializable  {


    SYSTEM_EXECUTION_SUCCESS("SYSTEM_EXECUTION_SUCCESS", "操作成功"),

    DIRECTORY_NAME_CONFLICT("DIRECTORY_NAME_CONFLICT", "已存在同名目录"),

    DIRECTORY_NOT_EMPTY("DIRECTORY_NOT_EMPTY", "目录不为空，无法删除。如需强制删除请设置 force=true"),

    FILE_ADD_CONFLICT("FILE_ADD_CONFLICT","该文件已添加至预案目录下"),

    CATEGORY_NAME_CONFLICT("CATEGORY_NAME_CONFLICT","已存在同名的预案类别"),

    FIELD_NAME_CONFLICT("FIELD_NAME_CONFLICT","该类别下已存在同名的字段"),


    SYSTEM_EXECUTION_ERROR("SYSTEM_EXECUTION_ERROR", "操作失败");


    public String getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }

    private String code;

    private String message;


    @Override
    public String toString() {
        return "KgpResultCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static KgpResultCode getValue(String code){
        for (KgpResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_EXECUTION_ERROR; // 默认执行错误
    }
}
