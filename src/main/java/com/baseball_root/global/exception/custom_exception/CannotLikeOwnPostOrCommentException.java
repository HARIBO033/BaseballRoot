package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class CannotLikeOwnPostOrCommentException extends CustomException {
    public CannotLikeOwnPostOrCommentException() {
        super(ErrorCode.CANNOT_LIKE_OWN_POST_OR_COMMENT_EXCEPTION);
    }
}
