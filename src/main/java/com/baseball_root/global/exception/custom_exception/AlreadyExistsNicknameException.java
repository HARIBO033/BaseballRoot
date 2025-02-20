package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class AlreadyExistsNicknameException extends CustomException {
    public AlreadyExistsNicknameException() {
        super(ErrorCode.ALREADY_EXISTS_NICKNAME_EXCEPTION);
    }
}
