package com.ryd.business.exception;

/**
 * 持仓业务异常
 * Created by chenji on 2016/4/27.
 */
public class PositionBusinessException extends Exception {
    public PositionBusinessException() {
    }

    public PositionBusinessException(String message) {
        super(message);
    }

    public PositionBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
