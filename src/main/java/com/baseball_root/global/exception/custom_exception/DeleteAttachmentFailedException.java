package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class DeleteAttachmentFailedException extends CustomException {
    public DeleteAttachmentFailedException() {
        super(ErrorCode.DELETE_ATTACHMENT_FAILED_EXCEPTION);
    }
}
