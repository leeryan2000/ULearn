package com.ulearn.dao.error;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/1 17:20
 */
public enum CommonSystemError implements CommonError{


    MQ_FAILED_CONSUME("90002", "MQ_FAILED_CONSUME"),
    MQ_FAILED_SENDMESSAGE("90001", "MQ_FAILED_SENDMESSAGE");
    ;

    private final String errCode;

    private final String errMsg;

    CommonSystemError(String errCode, String errMsg) {
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
