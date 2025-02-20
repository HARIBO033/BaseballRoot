package com.baseball_root.global.exception.custom_exception;

import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class InvalidIntegratedLinkIdException extends CustomException {
    public InvalidIntegratedLinkIdException() {
        super(ErrorCode.INVALID_INTEGRATED_LINK_EXCEPTION);
    }
}
