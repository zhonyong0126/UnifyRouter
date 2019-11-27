package com.feture.learnfilter.exception;

import com.feture.learnfilter.model.OpenApiResponse;

public abstract class BaseSystemException extends Exception {
    private final String _subCode;

    public BaseSystemException(String subCode) {
        _subCode = subCode;
    }

    /// <summary>
    /// 获取公共返回码
    /// </summary>
    /// <returns></returns>
    protected abstract int GetCode();

    /// <summary>
    /// 获取公共返回码描述
    /// </summary>
    /// <returns></returns>
    protected abstract String GetMessage();

    /// <summary>
    /// 获取公共返回码描述
    /// </summary>
    /// <returns></returns>
    protected String GetSubMessage() {
        return null;
    }

    /// <summary>
    /// 获取业务返回码
    /// </summary>
    /// <returns></returns>
    protected  String GetSubCode()
    {
        return _subCode;
    }

    public OpenApiResponse getOpenApiResponse() {
        return new OpenApiResponse(GetCode(), GetMessage(), GetSubCode(), GetSubMessage());
    }
}
