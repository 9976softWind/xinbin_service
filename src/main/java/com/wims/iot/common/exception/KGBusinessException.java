package com.wims.iot.common.exception;

import com.wims.iot.common.result.KgpResultCode;
import lombok.Getter;

/**
 * 自定义业务异常
 *
 * @author haoxr
 * @since 2022/7/31
 */
@Getter
public class KGBusinessException extends RuntimeException {

    public KgpResultCode resultCode;

    public KGBusinessException(KgpResultCode errorCode) {
        super(errorCode.getMsg());
        this.resultCode = errorCode;
    }
    public KGBusinessException(KgpResultCode errorCode,String message) {
        super(message);
        this.resultCode = errorCode;
    }

    public KGBusinessException(String message){
        super(message);
    }

    public KGBusinessException(String message, Throwable cause){
        super(message, cause);
    }

    public KGBusinessException(Throwable cause){
        super(cause);
    }


}
