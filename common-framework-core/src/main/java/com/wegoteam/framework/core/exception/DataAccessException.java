package com.wegoteam.framework.core.exception;

/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
public class DataAccessException extends Exception {
    private static final long serialVersionUID = -1219262335729891920L;

    /**
     * 构造方法
     * @param message
     */
    public DataAccessException(final String message) {
        super(message);
    }

    /**
     * 构造方法
     * @param cause
     */
    public DataAccessException(final Throwable cause) {
        super(cause);
    }

    /**
     * 构造方法
     * @param message
     * @param cause
     */
    public DataAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}

