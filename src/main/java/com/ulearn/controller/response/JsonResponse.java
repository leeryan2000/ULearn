package com.ulearn.controller.response;

import com.ulearn.dao.error.CommonError;
import lombok.Data;

import java.util.HashMap;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/1 13:03
 */

@Data
public class JsonResponse {

    private String code;

    private String msg;

    private HashMap data;

    public JsonResponse(String code, String msg, HashMap data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(HashMap data) {
        this.code = "0";
        this.msg = "success";
        this.data = data;
    }

    public JsonResponse() {
        this.code = "0";
        this.msg = "success";
    }

    public static JsonResponse ok() {
        return new JsonResponse();
    }

    public static JsonResponse ok(HashMap data) {
        return new JsonResponse(data);
    }

    public static JsonResponse error() {
        return new JsonResponse("500", "Unknown error");
    }

    public static JsonResponse error(CommonError error) {
        return new JsonResponse(error.getErrCode(), error.getErrMsg());
    }

    public static JsonResponse error(HashMap data) {
        return new JsonResponse("501", "Invalid field", data);
    }
}
