package com.baseball_root.global.exception.custom_exception;

import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class InvalidCommentIdException extends CustomException {
    public InvalidCommentIdException() {
        super(ErrorCode.INVALID_COMMENT_ID_EXCEPTION);
    }
}
