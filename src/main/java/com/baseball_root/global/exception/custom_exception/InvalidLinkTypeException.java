package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class InvalidLinkTypeException extends CustomException {
    public InvalidLinkTypeException() {
        super(ErrorCode.INVALID_LINK_TYPE_EXCEPTION);
    }
}
