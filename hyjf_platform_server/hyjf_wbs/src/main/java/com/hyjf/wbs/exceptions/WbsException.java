/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.exceptions;

/**
 * @author cui
 * @version WbsException, v0.1 2019/4/16 17:31
 */
public class WbsException extends RuntimeException {

    public WbsException() {
    }

    public WbsException(String message) {
        super(message);
    }

    public WbsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WbsException(Throwable cause) {
        super(cause);
    }

    public WbsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
