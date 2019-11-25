package com.feture.learnfilter.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feture.learnfilter.model.OpenApiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RewriteHttpBodyRequest extends HttpServletRequestWrapper {

    private static Logger logger = LoggerFactory.getLogger(RewriteHttpBodyRequest.class);
    /**
     * 原始请求体
     */
    private byte[] orginalRequestBody = null;

    /**
     * 真实请求体
     */
    private byte[] realRequestBody = null;//真实请求体

    /**
     * 请求实体
     */
    private OpenApiRequest openApiRequest = null;

    public RewriteHttpBodyRequest(HttpServletRequest request) throws IOException {
        super(request);
        orginalRequestBody = StreamUtils.copyToByteArray(request.getInputStream());
        realRequestBody = getRealRequestBody().getBytes();
    }


    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream bais = new ByteArrayInputStream(realRequestBody);
        return new ServletInputStream() {
            @Override
            public int read(){
                return bais.read();
            }
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) { }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    private String getRealRequestBody() {
        try {
            JSONObject object = JSON.parseObject(new String(orginalRequestBody));
            OpenApiRequest openApiRequest = new OpenApiRequest();
            openApiRequest.setBizContent(object.getString("BizContent"));
            openApiRequest.setMethod(object.getString("Method"));
            openApiRequest.setTimestamp(object.getString("Timestamp"));
            openApiRequest.setChannelKey(object.getString("ChannelKey"));
            openApiRequest.setSignType(object.getString("SignType"));
            openApiRequest.setCharset(object.getString("Charset"));
            openApiRequest.setVersion(object.getString("Version"));
            openApiRequest.setFormat(object.getString("Format"));
            openApiRequest.setSign(object.getString("Sign"));

            this.openApiRequest = openApiRequest;

            return openApiRequest.getBizContent();
        } catch (Exception e) {
            logger.error("getRealRequestBody error:{}", e);
        }
        return null;

    }

    public OpenApiRequest getOpenApiRequest() {
        return openApiRequest;
    }
}
