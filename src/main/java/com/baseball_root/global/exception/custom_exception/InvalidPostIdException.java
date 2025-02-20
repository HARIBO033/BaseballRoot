package com.baseball_root.global.exception.custom_exception;

import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class InvalidPostIdException extends CustomException {
    public InvalidPostIdException() {
        super(ErrorCode.INVALID_POST_ID_EXCEPTION);
    }
}
