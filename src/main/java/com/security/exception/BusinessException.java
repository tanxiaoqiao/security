package com.security.exception;


import com.security.constant.ErrorEnum;

public class BusinessException extends RuntimeException {
    public int code;
    public String msg;


    public BusinessException(ErrorEnum e) {
        this.code = e.getCode();
        this.msg = e.getMsg();
    }
}
