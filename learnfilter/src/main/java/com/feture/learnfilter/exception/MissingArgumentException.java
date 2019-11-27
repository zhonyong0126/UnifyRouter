package com.feture.learnfilter.exception;

import com.feture.learnfilter.consts.ResponseCode;

public class MissingArgumentException extends BaseSystemException {

    public MissingArgumentException(String subCode) {
        super(subCode);
    }

    @Override
    protected int GetCode() {
        return ResponseCode.MissingArgument.getCode();
    }

    @Override
    protected String GetMessage() {
        return ResponseCode.MissingArgument.getMessage();
    }
}
