package com.ulearn.controller.handler;

import com.ulearn.controller.response.JsonResponse;
import com.ulearn.dao.error.CommonRuntimeException;
import io.swagger.v3.core.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/1 17:41
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public JsonResponse exception() {
        return JsonResponse.error();
    }

    @ExceptionHandler(value = {CommonRuntimeException.class})
    public JsonResponse commonRuntimeException(CommonRuntimeException ex) {
        return JsonResponse.error(ex.getCommonError());
    }

    // Deals with the exception thrown by spring boot validating
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResponse handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return JsonResponse.error(errors);
    }
}
