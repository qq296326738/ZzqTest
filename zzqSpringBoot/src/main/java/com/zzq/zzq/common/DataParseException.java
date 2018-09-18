package com.zzq.zzq.common;

import com.zzq.zzq.exception.BaseException;

public class DataParseException extends BaseException {
    public DataParseException() {
    }

    public DataParseException(Throwable ex) {
        super(ex);
    }

    public DataParseException(String message) {
        super(message);
    }

    public DataParseException(String message, Throwable ex) {
        super(message, ex);
    }
}
