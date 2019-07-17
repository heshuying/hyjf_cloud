/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.exceptions;

/**
 * @author cui
 * @version WbsFundDetailsException, v0.1 2019/7/1 10:16
 */
public class WbsFundDetailsException extends RuntimeException {

    public WbsFundDetailsException() {
    }

    public WbsFundDetailsException(String message) {
        super(message);
    }

    public WbsFundDetailsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WbsFundDetailsException(Throwable cause) {
        super(cause);
    }

    public WbsFundDetailsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
