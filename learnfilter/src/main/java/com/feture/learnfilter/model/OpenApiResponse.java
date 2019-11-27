package com.feture.learnfilter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.feture.learnfilter.consts.ResponseCode;

public class OpenApiResponse {

    public OpenApiResponse() {
        this.code = ResponseCode.Success.getCode();
    }

    public OpenApiResponse(Object data) {
        this.code = ResponseCode.Success.getCode();
        this.data = data;
    }

    public OpenApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public OpenApiResponse(Integer code, String message, String subCode, String subMessage) {
        this.code = code;
        this.message = message;
        this.subCode = subCode;
        this.subMessage = subMessage;
    }

    /// <summary>
    /// 公共返回码，具体见NextPms.OpenAPI.Enums.ResponseCode
    /// </summary>
    @JsonProperty("Code")
    private Integer code;

    /// <summary>
    /// 公共返回码描述
    /// </summary>
    @JsonProperty("Message")
    private String message;

    /// <summary>
    /// 业务返回码
    /// </summary>
    @JsonProperty("SubCode")
    private String subCode;

    /// <summary>
    /// 业务返回码描述，参见具体的API接口文档
    /// </summary>
    @JsonProperty("SubMessage")
    private String subMessage;

    /// <summary>
    /// 业务返回值，如查询结果等
    /// </summary>
    @JsonProperty("Data")
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubMessage() {
        return subMessage;
    }

    public void setSubMessage(String subMessage) {
        this.subMessage = subMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
