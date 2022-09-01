package com.ulearn.controller.handler;

import com.ulearn.controller.response.JsonResponse;
import com.ulearn.dao.error.CommonError;
import com.ulearn.dao.error.CommonRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/1 17:41
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public JsonResponse UserException(CommonRuntimeException ex) {
        return JsonResponse.error(ex.getCommonError());
    }
}
