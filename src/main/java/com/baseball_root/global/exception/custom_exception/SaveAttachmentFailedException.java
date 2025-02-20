package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class SaveAttachmentFailedException extends CustomException {
    public SaveAttachmentFailedException() {
        super(ErrorCode.SAVE_ATTACHMENT_FAILED_EXCEPTION);
    }
}
