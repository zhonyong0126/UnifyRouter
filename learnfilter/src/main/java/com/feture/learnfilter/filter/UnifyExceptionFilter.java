package com.feture.learnfilter.filter;

import com.feture.learnfilter.consts.ResponseCode;
import com.feture.learnfilter.model.OpenApiResponse;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.feture.learnfilter.openapi"})
@Order(65536)
public class UnifyExceptionFilter {


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public OpenApiResponse handlerIllegalArgumentException(IllegalArgumentException e) {
        OpenApiResponse openApiResponse = new OpenApiResponse();

        openApiResponse.setCode(ResponseCode.InvalidArgument.getCode());
        openApiResponse.setMessage(e.getMessage());
        return openApiResponse;
    }



    @ExceptionHandler(Exception.class)
    @ResponseBody
    public OpenApiResponse handlerException(Exception e) {
        OpenApiResponse openApiResponse = new OpenApiResponse();

        openApiResponse.setCode(ResponseCode.UnknowError.getCode());
        openApiResponse.setMessage(e.getMessage());
        return openApiResponse;
    }
}
