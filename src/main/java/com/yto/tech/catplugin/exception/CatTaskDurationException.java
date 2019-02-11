package com.yto.tech.catplugin.exception;

public class CatTaskDurationException extends RuntimeException {
    public CatTaskDurationException() {
    }

    public CatTaskDurationException(String msg) {
        super(msg);
    }

    public CatTaskDurationException(String msg, Throwable e) {
        super(msg, e);
    }

    public CatTaskDurationException(Throwable e) {
        super(e);
    }

    public CatTaskDurationException(String msg, Throwable e, boolean enableSuppression, boolean writableStackTrace) {
        super(msg, e, enableSuppression, writableStackTrace);
    }
}
