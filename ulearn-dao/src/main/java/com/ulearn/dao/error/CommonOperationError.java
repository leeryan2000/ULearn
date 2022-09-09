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
    USER_ALREADY_LOGIN("10005", "USER_ALREADY_LOGIN"),

    // User post operation error
    POST_FAILED("20001", "POST_FAILED"),
    QUESTION_DOESNT_EXIST("20002", "QUESTION_DOESNT_EXIST"),
    VOTE_FAILED("20003", "VOTE_FAILED"),
    VOTE_DELETE_FAILED("20004", "VOTE_DELETE_FAILED"),
    CREATE_BOOKMARK_FAILED("20005", "BOOKMARK_FAILED"),
    FOLLOW_FAILED("20006", "FOLLOW_FAILED"),
    CREATOR_CANT_FOLLOW_OWN_POST("20007", "CREATOR_CANT_FOLLOW_OWN_POST"),
    CREATE_BOOKMARK_GROUP_FAILED("20008", "CREATE_BOOKMARK_GROUP_FAILED"),
    QUESTION_TAG_ADD_FAILED("20009", "QUESTION_TAG_ADD_FAILED"),
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
