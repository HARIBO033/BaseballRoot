package com.baseball_root.global.exception.custom_exception;

import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class EmailNotFoundException extends CustomException {
    public EmailNotFoundException() {
        super(ErrorCode.EMAIL_NOT_FOUND_EXCEPTION);
    }
}
