package com.feture.learnfilter.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.feture.learnfilter.consts.RequestConst;
import com.feture.learnfilter.consts.ResponseCode;
import com.feture.learnfilter.consts.ResponseSubCode;
import com.feture.learnfilter.exception.InvalidArgumentException;
import com.feture.learnfilter.exception.MissingArgumentException;
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

    private static final ThreadLocal<OpenApiRequest> unifyRequest = ThreadLocal.withInitial(() -> new OpenApiRequest());

    @Autowired
    private ChannelCredentialService channelCredentialService;


    public void verifyRequestBody(OpenApiRequest openApiRequest) throws Exception{

        if (null == openApiRequest)
            throw new IllegalArgumentException("非法请求");

        if (StringUtils.isEmpty(openApiRequest.getChannelKey()))
            throw new MissingArgumentException(ResponseSubCode.MISSING_CHANNEL_KEY);
        if (StringUtils.isEmpty(openApiRequest.getMethod()))
            throw new MissingArgumentException(ResponseSubCode.MISSING_METHOD);
        if (StringUtils.isEmpty(openApiRequest.getBizContent()))
            throw new MissingArgumentException(ResponseSubCode.MISSING_HTTP_BODY);
        if (StringUtils.isEmpty(openApiRequest.getCharset()))
            throw new MissingArgumentException(ResponseSubCode.MISSING_CHARSET);
        if (StringUtils.isEmpty(openApiRequest.getVersion()))
            throw new MissingArgumentException(ResponseSubCode.MISSING_VERSION);
        if (StringUtils.isEmpty(openApiRequest.getFormat()))
            throw new MissingArgumentException(ResponseSubCode.MISSING_FORMAT);
        if (StringUtils.isEmpty(openApiRequest.getTimestamp()))
            throw new MissingArgumentException(ResponseSubCode.MISSING_TIMESTAMP);
        if (StringUtils.isEmpty(openApiRequest.getSignType()))
            throw new MissingArgumentException(ResponseSubCode.MISSING_SIGN_TYPE);
        if (StringUtils.isEmpty(openApiRequest.getSign()))
            throw new MissingArgumentException(ResponseSubCode.MISSING_SIGN);

        String appKey = channelCredentialService.getAppKeyByChannelKey(openApiRequest.getChannelKey());
        if (StringUtils.isEmpty(appKey))
            throw new InvalidArgumentException(ResponseSubCode.INVALID_CHANNEL_KEY);

        if (!RequestConst.CharsetSet.contains(openApiRequest.getCharset()))
            throw new InvalidArgumentException(ResponseSubCode.INVALID_CHARSET);
        if (!RequestConst.VersionSet.contains(openApiRequest.getVersion()))
            throw new InvalidArgumentException(ResponseSubCode.INVALID_VERSION);
        if (!RequestConst.FormatSet.contains(openApiRequest.getFormat()))
            throw new InvalidArgumentException(ResponseSubCode.INVALID_FORMAT);
        if (!RequestConst.SignTypeSet.contains(openApiRequest.getSignType()))
            throw new InvalidArgumentException(ResponseSubCode.INVALID_SIGN_TYPE);
        if (openApiRequest.getTimestamp().length() != 19)
            throw new InvalidArgumentException(ResponseSubCode.INVALID_TIMESTAMP);

//        //超过两分钟的请求视为无效
//        Date requestDate = DateUtils.parseStringToDate(openApiRequest.getTimestamp(),DateUtils.STANDARD_DATE_PATTERN);
//        Long secondDiff = DateUtils.getSecondDistance(new Date(), requestDate);
//        if (secondDiff < 0 || secondDiff > 120)
//            throw new InvalidArgumentException(ResponseSubCode.INVALID_TIMESTAMP);

        String signPlainText = StringUtil.createSortedLinkString(openApiRequest) + appKey;
        String sign = "SHA256".equalsIgnoreCase(openApiRequest.getSignType()) ? OpenAPISignUtil.sha256(signPlainText) : OpenAPISignUtil.md5(signPlainText);
        if (!sign.toUpperCase().equals(openApiRequest.getSign().toUpperCase())) {
            throw new InvalidArgumentException(ResponseSubCode.INVALID_SIGN);
        }

        unifyRequest.set(openApiRequest);
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

    /**
     * 获取统一请求参数
     * @return
     */
    public OpenApiRequest getUnifyRequest() {
        return unifyRequest.get();
    }
}
