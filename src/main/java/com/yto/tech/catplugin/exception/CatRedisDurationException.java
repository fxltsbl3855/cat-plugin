package com.yto.tech.catplugin.exception;

public class CatRedisDurationException extends RuntimeException {
    public CatRedisDurationException() {
    }

    public CatRedisDurationException(String msg) {
        super(msg);
    }

    public CatRedisDurationException(String msg, Throwable e) {
        super(msg, e);
    }

    public CatRedisDurationException(Throwable e) {
        super(e);
    }

    public CatRedisDurationException(String msg, Throwable e, boolean enableSuppression, boolean writableStackTrace) {
        super(msg, e, enableSuppression, writableStackTrace);
    }
}
