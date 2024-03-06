package com.ulearn.controller.response;

import cn.hutool.core.lang.hash.Hash;
import com.ulearn.dao.error.CommonError;
import lombok.Data;
import org.springframework.data.redis.hash.HashMapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/1 13:03
 */

@Data
public class JsonResponse {

    private String code;

    private String msg;

    private List<HashMap> data;

    private HashMap error;

    public JsonResponse(String code, String msg, List<HashMap> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(List<HashMap> data) {
        this.code = "0";
        this.msg = "success";
        this.data = data;
    }

    public JsonResponse(String code, String msg, HashMap error) {
        this.code = code;
        this.msg = msg;
        this.error = error;
    }

    public JsonResponse() {
        this.code = "0";
        this.msg = "success";
    }

    public static JsonResponse ok() {
        return new JsonResponse();
    }

    public static JsonResponse ok(List<HashMap> data) {
        return new JsonResponse(data);
    }

    public static JsonResponse error() {
        return new JsonResponse("500", "Unknown error");
    }

    public static JsonResponse error(CommonError error) {
        return new JsonResponse(error.getErrCode(), error.getErrMsg());
    }

    public static JsonResponse error(HashMap error) {
        return new JsonResponse("501", "Invalid field", error);
    }
}
