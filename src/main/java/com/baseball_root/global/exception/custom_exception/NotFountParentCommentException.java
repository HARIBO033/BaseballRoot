package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class NotFountParentCommentException extends CustomException {
    public NotFountParentCommentException() {
        super(ErrorCode.NOT_FOUND_PARENT_COMMENT_EXCEPTION);
    }
}
