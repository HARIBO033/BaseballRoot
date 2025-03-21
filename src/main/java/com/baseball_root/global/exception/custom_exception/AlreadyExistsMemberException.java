package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class AlreadyExistsMemberException extends CustomException {
    public AlreadyExistsMemberException() {
        super(ErrorCode.ALREADY_EXISTS_MEMBER_EXCEPTION);
    }
}
