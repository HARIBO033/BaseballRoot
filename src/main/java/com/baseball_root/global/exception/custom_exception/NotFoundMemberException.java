package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class NotFoundMemberException extends CustomException {
    public NotFoundMemberException() {
        super(ErrorCode.NOT_FOUND_MEMBER_EXCEPTION);
    }
}
