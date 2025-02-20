package com.baseball_root.global.exception.custom_exception;

import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN_EXCEPTION);
    }
}
