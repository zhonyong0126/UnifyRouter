package com.feture.learnfilter.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.feture.learnfilter.consts.ResponseCode;
import com.feture.learnfilter.model.OpenApiRequest;
import com.feture.learnfilter.model.OpenApiResponse;
import com.feture.learnfilter.util.OpenAPISignUtil;
import com.feture.learnfilter.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class RequestService {

    private static Logger logger = LoggerFactory.getLogger(RequestService.class);

    @Autowired
    private ChannelCredentialService channelCredentialService;


    public void verifyRequestBody(OpenApiRequest openApiRequest) throws IllegalArgumentException{
        String appKey = channelCredentialService.getAppKeyByChannelKey(openApiRequest.getChannelKey());
        if (StringUtils.isEmpty(appKey))
            throw new IllegalArgumentException("非法渠道");
        String signPlainText = StringUtil.createSortedLinkString(openApiRequest) + appKey;
        String sign = "SHA256".equalsIgnoreCase(openApiRequest.getSignType()) ? OpenAPISignUtil.sha256(signPlainText) : OpenAPISignUtil.md5(signPlainText);
        if (!sign.toUpperCase().equals(openApiRequest.getSign().toUpperCase())) {
            throw new IllegalArgumentException("非法签名");
        }
    }

    public void mockErrorResponse(ServletResponse response, String errorMessage) {

        OpenApiResponse openApiResponse = new OpenApiResponse();
        openApiResponse.setMessage(errorMessage);
        openApiResponse.setCode(ResponseCode.InvalidRequest.getCode());
        try {
            response.getOutputStream().write(JSON.toJSONString(openApiResponse, SerializerFeature.WriteMapNullValue).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("mockResponse error(UnsupportedEncodingException) {}", e);
        } catch (IOException e) {
            logger.error("mockResponse error(IOException) {}", e);
        }
    }
}
