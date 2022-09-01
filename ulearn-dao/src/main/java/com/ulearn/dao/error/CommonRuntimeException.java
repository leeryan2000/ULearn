package com.ulearn.dao.error;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/6/27 19:12
 */
public class CommonRuntimeException extends RuntimeException{

    private final CommonError commonError;

    public CommonRuntimeException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    public CommonError getCommonError() {
        return commonError;
    }
}
