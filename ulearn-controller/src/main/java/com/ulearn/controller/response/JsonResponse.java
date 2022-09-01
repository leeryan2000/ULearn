package com.ulearn.controller.response;

import cn.hutool.core.lang.hash.Hash;
import com.ulearn.dao.error.CommonError;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/1 13:03
 */

@Data
public class JsonResponse<T> {

    private String code;

    private String msg;

    private T data;

    public JsonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(T data) {
        this.code = "0";
        this.msg = "success";
        this.data = data;
    }

    public JsonResponse() {
        this.code = "0";
        this.msg = "success";
    }

    public static JsonResponse<String> ok() {
        return new JsonResponse<>();
    }

    public static JsonResponse<HashMap> ok(HashMap data) {
        return new JsonResponse<>(data);
    }

    public static JsonResponse<String> error() {
        return new JsonResponse<>("500", "Unknown error");
    }

    public static JsonResponse<String> error(CommonError error) {
        return new JsonResponse<>(error.getErrCode(), error.getErrMsg());
    }
}
