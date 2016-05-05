package com.ryd.business.exception;

/**
 * 结算业务异常
 * Created by chenji on 2016/4/27.
 */
public class SettleBusinessException extends Exception {
    public SettleBusinessException() {
    }

    public SettleBusinessException(String message) {
        super(message);
    }

    public SettleBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
