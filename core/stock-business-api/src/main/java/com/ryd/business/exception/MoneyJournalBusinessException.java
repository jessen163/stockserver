package com.ryd.business.exception;

/**
 * 帐户业务异常
 * Created by chenji on 2016/4/27.
 */
public class MoneyJournalBusinessException extends Exception {
    public MoneyJournalBusinessException() {
    }

    public MoneyJournalBusinessException(String message) {
        super(message);
    }

    public MoneyJournalBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
