package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class InvalidRefreshTokenException extends CustomException {
    public InvalidRefreshTokenException() {
        super(ErrorCode.INVALID_REFRESH_TOKEN_EXCEPTION);
    }
}
