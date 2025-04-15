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

    private String message;

    public static <T> KgpResult<T> success() {
        return success(null);
    }

    public static <T> KgpResult<T> success(T data) {
        KgpResult<T> result = new KgpResult<>();
        result.setCode(KgpResultCode.SYSTEM_EXECUTION_SUCCESS.getCode());
        result.setMessage(KgpResultCode.SYSTEM_EXECUTION_SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> KgpResult<T> failed() {
        return result(KgpResultCode.SYSTEM_EXECUTION_ERROR.getCode(), KgpResultCode.SYSTEM_EXECUTION_ERROR.getMsg(), null);
    }

    public static <T> KgpResult<T> failed(String msg) {
        return result(KgpResultCode.SYSTEM_EXECUTION_ERROR.getCode(), msg, null);
    }

    public static <T> KgpResult<T> judge(boolean status) {
        if (status) {
            return success();
        } else {
            return failed();
        }
    }
    public static <T> KgpResult<T> judge(boolean status,KgpResultCode resultCode) {
        if (status) {
            return success();
        } else {
            return failed(resultCode);
        }
    }

    public static <T> KgpResult<T> failed(KgpResultCode resultCode) {
        return result(resultCode.getCode(), resultCode.getMsg(), null);
    }

    public static <T> KgpResult<T> failed(KgpResultCode resultCode, String message) {
        return result(resultCode.getCode(), message, null);
    }

    private static <T> KgpResult<T> result(KgpResultCode resultCode, T data) {
        return result(resultCode.getCode(), resultCode.getMsg(), data);
    }

    private static <T> KgpResult<T> result(String code, String message, T data) {
        KgpResult<T> result = new KgpResult<>();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    public static boolean isSuccess(KgpResult<?> result) {
        return result != null && KgpResultCode.SYSTEM_EXECUTION_SUCCESS.getCode().equals(result.getCode());
    }
}
