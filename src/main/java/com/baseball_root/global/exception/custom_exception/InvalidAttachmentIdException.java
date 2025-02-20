package com.baseball_root.global.exception.custom_exception;

import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class InvalidAttachmentIdException extends CustomException {
    public InvalidAttachmentIdException() {
        super(ErrorCode.INVALID_ATTACHMENT_ID_EXCEPTION);
    }
}
