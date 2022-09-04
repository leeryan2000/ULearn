package com.ulearn.dao.error;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/1 17:23
 */
public enum CommonOperationError implements CommonError{
    // User account operation error
    USER_DOESNT_EXIST("10001", "USER_DOESNT_EXIST"),
    USER_SIGNUP_FAILED("10002", "USER_SIGNUP_FAILED"),
    USER_EXIST("10003", "USER_ALREADY_EXIST"),
    USER_WRONG_PASSWORD("10004", "USER_WRONG_PASSWORD"),

    // User post operation error
    POST_FAILED("20001", "POST_FAILED"),
    ;

    private final String errCode;

    private final String errMsg;

    CommonOperationError(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public String getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }
}
