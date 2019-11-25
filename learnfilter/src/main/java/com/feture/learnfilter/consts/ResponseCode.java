package com.feture.learnfilter.consts;

public enum ResponseCode {
    Success(10000, "成功"),
    UnknowError(20000, "内部错误"),
    InvalidRequest(30000, "非法请求"),
    MissingArgument(40001, "缺少必选参数"),
    InvalidArgument(40002, "非法参数"),
    BusinessFailure(50000, "业务处理失败"),
    InsufficientPermissions(60000, "权限不足");


    private Integer code;

    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
