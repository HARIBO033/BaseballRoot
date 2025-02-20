package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class AccessDeniedException extends CustomException {
    public AccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED_EXCEPTION);
    }
}
