package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class VerificationCodeNotValidException extends CustomException {
    public VerificationCodeNotValidException() {
        super(ErrorCode.VERIFICATION_CODE_NOT_VALID_EXCEPTION);
    }
}
