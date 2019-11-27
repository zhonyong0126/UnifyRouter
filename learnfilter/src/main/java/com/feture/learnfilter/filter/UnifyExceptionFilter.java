package com.feture.learnfilter.filter;

import com.feture.learnfilter.consts.RequestConst;
import com.feture.learnfilter.consts.ResponseCode;
import com.feture.learnfilter.exception.BaseSystemException;
import com.feture.learnfilter.model.OpenApiRequest;
import com.feture.learnfilter.model.OpenApiResponse;
import com.feture.learnfilter.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.feture.learnfilter.openapi"})
@Order(65536)
public class UnifyExceptionFilter {

    private static Logger logger = LoggerFactory.getLogger(UnifyExceptionFilter.class);

    @Autowired
    private RequestService requestService;


    @ExceptionHandler(BaseSystemException.class)
    public OpenApiResponse handlerSystemException(BaseSystemException e) {
        logError(e);
        return e.getOpenApiResponse();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public OpenApiResponse handlerIllegalArgumentException(IllegalArgumentException e) {
        logError(e);
        OpenApiResponse openApiResponse = new OpenApiResponse();

        openApiResponse.setCode(ResponseCode.InvalidArgument.getCode());
        openApiResponse.setMessage(e.getMessage());
        return openApiResponse;
    }



    @ExceptionHandler(Exception.class)
    public OpenApiResponse handlerException(Exception e) {
        logError(e);
        OpenApiResponse openApiResponse = new OpenApiResponse();

        openApiResponse.setCode(ResponseCode.UnknowError.getCode());
        openApiResponse.setMessage(e.getMessage());
        return openApiResponse;
    }


    private void logError(Exception e) {
        OpenApiRequest openApiRequest = requestService.getUnifyRequest();

        logger.error("请求方法：{}，方法请求参数：{}，统一请求参数：{}，错误信息：{}，错误堆栈：", RequestConst.RouteDispatchPrefix + "/" + openApiRequest.getMethod().replace(".", "/"), openApiRequest.getBizContent(), openApiRequest.toString(), e.getMessage(), e);
    }
}
