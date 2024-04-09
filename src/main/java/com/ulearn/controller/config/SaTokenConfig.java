package com.ulearn.controller.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/19 16:52
 */

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Config Sa-Token interceptors, turn on token authentication using annotation
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }
}
