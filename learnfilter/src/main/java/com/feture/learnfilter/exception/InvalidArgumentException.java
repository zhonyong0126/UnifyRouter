package com.feture.learnfilter.exception;

import com.feture.learnfilter.consts.ResponseCode;

public class InvalidArgumentException extends BaseSystemException {
    public InvalidArgumentException(String subCode) {
        super(subCode);
    }

    @Override
    protected int GetCode() {
        return ResponseCode.InvalidArgument.getCode();
    }

    @Override
    protected String GetMessage() {
        return ResponseCode.InvalidArgument.getMessage();
    }
}
