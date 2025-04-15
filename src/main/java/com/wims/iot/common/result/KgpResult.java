package com.wims.iot.common.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结构体
 *
 * @author haoxr
 * @since 2022/1/30
 **/
@Data
public class KgpResult<T> implements Serializable {

    private String code;

    private T data;


    public static <T> KgpResult<T> success() {
        return success(null);
    }

    public static <T> KgpResult<T> success(T data) {
        KgpResult<T> result = new KgpResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    public static <T> KgpResult<T> failed() {
        return result(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(),null);
    }

    public static <T> KgpResult<T> failed(KgpResultCode resultCode) {
        return result(resultCode.getCode(), null);
    }

    private static <T> KgpResult<T> result(KgpResultCode resultCode, T data) {
        return result(resultCode.getCode(),  data);
    }

    private static <T> KgpResult<T> result(String code,  T data) {
        KgpResult<T> result = new KgpResult<>();
        result.setCode(code);
        result.setData(data);
        return result;
    }

    public static boolean isSuccess(KgpResult<?> result) {
        return result != null && ResultCode.SUCCESS.getCode().equals(result.getCode());
    }
}
