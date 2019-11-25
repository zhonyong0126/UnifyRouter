package com.feture.learnfilter.filter;

import com.alibaba.fastjson.JSON;
import com.feture.learnfilter.model.OpenApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = {"com.feture.learnfilter.openapi"})
public class UnifyResponseFilter implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (null == body) {
            return new OpenApiResponse();
        }

        if (body instanceof OpenApiResponse) {
            return body;
        }

        if (body instanceof String) {
            return JSON.toJSON(new OpenApiResponse(body)).toString();
        }

        return new OpenApiResponse(body);
    }
}
