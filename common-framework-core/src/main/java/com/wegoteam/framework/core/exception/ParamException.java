package com.wegoteam.framework.core.exception;

/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
public class ParamException extends RuntimeException {
    private static final long serialVersionUID = -1L;

    private  String errorCode;

    private String errorMsg;

    public ParamException(String errorCode, String errorMsg) {
        super(errorCode);
        this.errorCode = errorCode;
        if (errorMsg.length() > 256) {
            errorMsg = errorMsg.substring(0, 255);
        }
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}

