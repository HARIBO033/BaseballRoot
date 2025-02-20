package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class AlreadyExistsEmailException extends CustomException {
    public AlreadyExistsEmailException() {
        super(ErrorCode.ALREADY_EXISTS_EMAIL_EXCEPTION);
    }
}
