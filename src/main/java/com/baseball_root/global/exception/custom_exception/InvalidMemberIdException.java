package com.baseball_root.global.exception.custom_exception;

import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class InvalidMemberIdException extends CustomException {
    public InvalidMemberIdException() {
        super(ErrorCode.INVALID_MEMBER_ID_EXCEPTION);
    }
}
