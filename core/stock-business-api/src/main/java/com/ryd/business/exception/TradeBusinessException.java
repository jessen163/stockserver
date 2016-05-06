package com.ryd.business.exception;

/**
 * 交易业务异常
 * Created by chenji on 2016/4/27.
 */
public class TradeBusinessException extends Exception {
    public TradeBusinessException() {
    }

    public TradeBusinessException(String message) {
        super(message);
    }

    public TradeBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
