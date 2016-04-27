package com.ryd.business.exception;

/**
 * 报价业务异常
 * Created by chenji on 2016/4/27.
 */
public class QuoteBusinessException extends Exception {
    public QuoteBusinessException() {
    }

    public QuoteBusinessException(String message) {
        super(message);
    }

    public QuoteBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
