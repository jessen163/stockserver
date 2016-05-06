package com.ryd.business.exception;

/**
 * 帐户业务异常
 * Created by chenji on 2016/4/27.
 */
public class AccountBusinessException extends Exception {
    public AccountBusinessException() {
    }

    public AccountBusinessException(String message) {
        super(message);
    }

    public AccountBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
