package com.feture.learnfilter.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.feture.learnfilter.service.ChannelCredentialService;
import com.feture.learnfilter.util.JsonUtils;
import com.feture.learnfilter.util.OpenAPISignUtil;
import com.feture.learnfilter.util.StringUtil;

public class OpenApiRequest {

    @JsonProperty("ChannelKey")
    private String channelKey;

    @JsonProperty("Method")
    private String method;

    @JsonProperty("BizContent")
    private String bizContent;

    @JsonProperty("Sign")
    private String sign;

    @JsonProperty("SignType")
    private String signType;

    @JsonProperty("Format")
    private String format;

    @JsonProperty("Charset")
    private String charset;

    @JsonProperty("Version")
    private String version;

    @JsonProperty("Timestamp")
    private String timestamp;


    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


     static void  setHelloRequest(OpenApiRequest openApiRequest) {
        HelloReq helloReq = new HelloReq();
        helloReq.setA(1);
        helloReq.setB("哈哈哈哈");

        openApiRequest.setBizContent(JSON.toJSONString(helloReq));
        openApiRequest.setVersion("1");
        openApiRequest.setMethod("test.hello1");
        openApiRequest.setCharset("2");
        openApiRequest.setSignType("22");
        openApiRequest.setChannelKey("qinghotel");
        openApiRequest.setSign("333");
        openApiRequest.setTimestamp("4444");

    }

    static void  setNextPMSAddCacheRequest(OpenApiRequest openApiRequest) {
        TestCacheModel testCacheModel = new TestCacheModel();
        testCacheModel.setKey("aaaa");

        openApiRequest.setBizContent(JSON.toJSONString(testCacheModel));
        openApiRequest.setVersion("1.0");
        openApiRequest.setMethod("Test.AddCache");
        openApiRequest.setCharset("utf-8");
        openApiRequest.setSignType("SHA256");
        openApiRequest.setChannelKey("wechat");
        openApiRequest.setSign("333");
        openApiRequest.setFormat("json");
        openApiRequest.setTimestamp("2019-11-22 12:00:00");

    }

    static void  setMockExceptionRequest(OpenApiRequest openApiRequest) {
        TestCacheModel testCacheModel = new TestCacheModel();
        testCacheModel.setKey("aaaa");

        openApiRequest.setBizContent(JsonUtils.objectToString(testCacheModel));
        openApiRequest.setVersion("1.0");
        openApiRequest.setMethod("Test.MockException");
        openApiRequest.setCharset("utf-8");
        openApiRequest.setSignType("SHA256");
        openApiRequest.setChannelKey("wechat");
        openApiRequest.setSign("333");
        openApiRequest.setFormat("json");
        openApiRequest.setTimestamp("2019-11-22 12:00:00");

    }

    @Override
    public String toString() {
        return "OpenApiRequest{" +
                "channelKey='" + channelKey + '\'' +
                ", method='" + method + '\'' +
                ", bizContent='" + bizContent + '\'' +
                ", sign='" + sign + '\'' +
                ", signType='" + signType + '\'' +
                ", format='" + format + '\'' +
                ", charset='" + charset + '\'' +
                ", version='" + version + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public static void main(String[] args) {
        ChannelCredentialService channelCredentialService = new ChannelCredentialService();
        OpenApiRequest openApiRequest = new OpenApiRequest();

        //setHelloRequest(openApiRequest)
        //setNextPMSAddCacheRequest(openApiRequest);
        setMockExceptionRequest(openApiRequest);

        //AddCache

        String signPlainText=StringUtil.createSortedLinkString(openApiRequest)+channelCredentialService.getAppKeyByChannelKey(openApiRequest.getChannelKey());
        String sign= OpenAPISignUtil.sha256(signPlainText);
        openApiRequest.setSign(sign);

        String requestJson=JsonUtils.objectToString(openApiRequest);

        System.out.println("sign："+sign);

        System.out.println(requestJson);
    }
}
